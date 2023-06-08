

// SPDX-License-Identifier: MIT
pragma solidity ^0.8.4;


// Template used to initialize state variables 
contract Bank {

    address owner;
    address payable receiver;
    string uniqueId;
    uint256 funds;
    mapping(string => bool) wallets;
mapping(string => bool) private list;

event FundsAdded(address indexed sender, uint256 amount);
    event TransferInitiated(address indexed sender, address indexed receiver, uint256 amount);
    event TransferCompleted(address indexed sender, address indexed receiver, uint256 amount);
    event TransferFailed(address indexed sender, address indexed receiver, uint256 amount, string reason);
    event gasNeeded(uint gas);

  constructor(address _owner, address payable _receiver, string memory _uniqueId) {
        owner = _owner;
        receiver = _receiver;
        uniqueId = _uniqueId;
        funds = 0;
    }

 function getBankFunds() public view returns (uint256) {
        return funds;
    }

    function getOwner() public view returns (address) {
        return owner;
    }
    
    function addFunds() external payable {
       // require(msg.value == _amount, "Amount must be equal to value sent");
        funds += msg.value;
        emit FundsAdded(msg.sender, msg.value);

    }

     

   function setWallet(string memory _wallet) public {
        wallets[_wallet] = true;
    }

     function contains(string memory _wallet) public view returns (bool) {
        return wallets[_wallet];
    }

    function sendFunds() external {
        //require(msg.sender == owner, "Only the owner can send funds");
        require(funds > 0, "No funds available to send");
        require(receiver != address(0), "Invalid receiver address");
        //uint256 gasRequired = calculateGasRequired(receiver, funds);

   
      emit TransferInitiated(msg.sender, receiver, funds);

        // Send the funds and update the state
        // bool success = false;
     receiver.transfer(funds);

    }

    function deposit() public payable {}


    function calculateGasRequired(address to, uint256 value) internal view returns (uint256) {
    uint256 gasLimit = block.gaslimit;
    uint256 gasPrice = tx.gasprice;
    uint256 intrinsicGas = 21000; // gas required for a simple ether transfer
    uint256 transferGas = (value / 32) + 7000; // gas required for a transfer
    uint256 totalGas = intrinsicGas + transferGas;

    if (to.balance == 0) {
        totalGas += 24000; // gas required for creating a contract
    }

    return totalGas + (totalGas * gasPrice / gasLimit);
}
   
}


contract BankFactory {
    event MyContractCreated(address newContract);

  
  //keep track of created Bank addresses in array 
  Bank[] public list_of_banks;

    mapping(string => Bank) bankList;

  // function arguments are passed to the constructor of the new created contract 
  function createBank(address _owner,address payable _receiver,string memory _uniqueId) external payable{
    Bank bank = new Bank(_owner,_receiver,_uniqueId);
    bank.addFunds{value: msg.value}();
    bank.setWallet(_uniqueId);

    list_of_banks.push(bank);
    bankList[_uniqueId] = bank;
    // Transfer the sent ether to the new Bank instance
    emit MyContractCreated(address(bank));

  }


  function getBankAddress(string memory uniqueId) public view returns (address) {
    Bank bank = bankList[uniqueId];
    require(address(bank) != address(0), "Bank not found");
    return address(bank);
}

  function getBankDetails(string memory _uniqueId) public view returns(uint256){
    
    Bank bank = bankList[_uniqueId];

    require(address(bank) != address(0), "Bank not found");
        return bank.getBankFunds();
  }


  function sendFunds(string memory _uniqueId) public {
        Bank bank = bankList[_uniqueId];
        require(address(bank) != address(0), "Bank not found");
        bank.sendFunds();
    }

    function getContracts(address ownerAddress) public view returns (address[] memory) {
    uint count = 0;
    for (uint i = 0; i < list_of_banks.length; i++) {
        if (list_of_banks[i].getOwner() == ownerAddress) {
            count++;
        }
    }
    address[] memory contracts = new address[](count);
    uint j = 0;
    for (uint i = 0; i < list_of_banks.length; i++) {
        if (list_of_banks[i].getOwner() == ownerAddress) {
            contracts[j] = address(list_of_banks[i]);
            j++;
        }
    }
    return contracts;
}

  
}
