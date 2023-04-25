package com.example.finalyearproject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Bank extends Contract {
    public static final String BINARY = "0x60806040523480156200001157600080fd5b5060405162000f7d38038062000f7d833981810160405281019062000037919062000318565b826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060029081620000c99190620005de565b506000600381905550505050620006c5565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006200011c82620000ef565b9050919050565b6200012e816200010f565b81146200013a57600080fd5b50565b6000815190506200014e8162000123565b92915050565b60006200016182620000ef565b9050919050565b620001738162000154565b81146200017f57600080fd5b50565b600081519050620001938162000168565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b620001ee82620001a3565b810181811067ffffffffffffffff8211171562000210576200020f620001b4565b5b80604052505050565b600062000225620000db565b9050620002338282620001e3565b919050565b600067ffffffffffffffff821115620002565762000255620001b4565b5b6200026182620001a3565b9050602081019050919050565b60005b838110156200028e57808201518184015260208101905062000271565b60008484015250505050565b6000620002b1620002ab8462000238565b62000219565b905082815260208101848484011115620002d057620002cf6200019e565b5b620002dd8482856200026e565b509392505050565b600082601f830112620002fd57620002fc62000199565b5b81516200030f8482602086016200029a565b91505092915050565b600080600060608486031215620003345762000333620000e5565b5b600062000344868287016200013d565b9350506020620003578682870162000182565b925050604084015167ffffffffffffffff8111156200037b576200037a620000ea565b5b6200038986828701620002e5565b9150509250925092565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680620003e657607f821691505b602082108103620003fc57620003fb6200039e565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302620004667fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8262000427565b62000472868362000427565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b6000620004bf620004b9620004b3846200048a565b62000494565b6200048a565b9050919050565b6000819050919050565b620004db836200049e565b620004f3620004ea82620004c6565b84845462000434565b825550505050565b600090565b6200050a620004fb565b62000517818484620004d0565b505050565b5b818110156200053f576200053360008262000500565b6001810190506200051d565b5050565b601f8211156200058e57620005588162000402565b620005638462000417565b8101602085101562000573578190505b6200058b620005828562000417565b8301826200051c565b50505b505050565b600082821c905092915050565b6000620005b36000198460080262000593565b1980831691505092915050565b6000620005ce8383620005a0565b9150826002028217905092915050565b620005e98262000393565b67ffffffffffffffff811115620006055762000604620001b4565b5b620006118254620003cd565b6200061e82828562000543565b600060209050601f83116001811462000656576000841562000641578287015190505b6200064d8582620005c0565b865550620006bd565b601f198416620006668662000402565b60005b82811015620006905784890151825560018201915060208501945060208101905062000669565b86831015620006b05784890151620006ac601f891682620005a0565b8355505b6001600288020188555050505b505050505050565b6108a880620006d56000396000f3fe6080604052600436106100705760003560e01c80638d68cf591161004e5780638d68cf5914610108578063a26759cb1461011f578063a99ba95c14610129578063d0e30db01461015257610070565b80634cb14f12146100755780636833d54f146100a0578063893d20e8146100dd575b600080fd5b34801561008157600080fd5b5061008a61015c565b604051610097919061044e565b60405180910390f35b3480156100ac57600080fd5b506100c760048036038101906100c291906105c3565b610166565b6040516100d49190610627565b60405180910390f35b3480156100e957600080fd5b506100f261019b565b6040516100ff9190610683565b60405180910390f35b34801561011457600080fd5b5061011d6101c4565b005b610127610390565b005b34801561013557600080fd5b50610150600480360381019061014b91906105c3565b6103f9565b005b61015a610433565b005b6000600354905090565b6000600482604051610178919061070f565b908152602001604051809103902060009054906101000a900460ff169050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600060035411610209576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161020090610783565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff160361029a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610291906107ef565b60405180910390fd5b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f0cb34e106629dce34b632826bbba6b38e39c5957eb06a27e76efcc3d55da46b560035460405161031b919061044e565b60405180910390a3600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc6003549081150290604051600060405180830381858888f1935050505015801561038d573d6000803e3d6000fd5b50565b34600360008282546103a2919061083e565b925050819055503373ffffffffffffffffffffffffffffffffffffffff167f8fe10ae416f22f5e5220b0018a6c1d4ff534d6aa3a471f2a20cb7747fe63e5b9346040516103ef919061044e565b60405180910390a2565b600160048260405161040b919061070f565b908152602001604051809103902060006101000a81548160ff02191690831515021790555050565b565b6000819050919050565b61044881610435565b82525050565b6000602082019050610463600083018461043f565b92915050565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6104d082610487565b810181811067ffffffffffffffff821117156104ef576104ee610498565b5b80604052505050565b6000610502610469565b905061050e82826104c7565b919050565b600067ffffffffffffffff82111561052e5761052d610498565b5b61053782610487565b9050602081019050919050565b82818337600083830152505050565b600061056661056184610513565b6104f8565b90508281526020810184848401111561058257610581610482565b5b61058d848285610544565b509392505050565b600082601f8301126105aa576105a961047d565b5b81356105ba848260208601610553565b91505092915050565b6000602082840312156105d9576105d8610473565b5b600082013567ffffffffffffffff8111156105f7576105f6610478565b5b61060384828501610595565b91505092915050565b60008115159050919050565b6106218161060c565b82525050565b600060208201905061063c6000830184610618565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061066d82610642565b9050919050565b61067d81610662565b82525050565b60006020820190506106986000830184610674565b92915050565b600081519050919050565b600081905092915050565b60005b838110156106d25780820151818401526020810190506106b7565b60008484015250505050565b60006106e98261069e565b6106f381856106a9565b93506107038185602086016106b4565b80840191505092915050565b600061071b82846106de565b915081905092915050565b600082825260208201905092915050565b7f4e6f2066756e647320617661696c61626c6520746f2073656e64000000000000600082015250565b600061076d601a83610726565b915061077882610737565b602082019050919050565b6000602082019050818103600083015261079c81610760565b9050919050565b7f496e76616c696420726563656976657220616464726573730000000000000000600082015250565b60006107d9601883610726565b91506107e4826107a3565b602082019050919050565b60006020820190508181036000830152610808816107cc565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061084982610435565b915061085483610435565b925082820190508082111561086c5761086b61080f565b5b9291505056fea264697066735822122099ad1a08c05d3e5b2f0695d87e75d5a553c191e46ef6d397dd139dee8cf4556e64736f6c63430008110033";

    public static final String FUNC_GETBANKFUNDS = "getBankFunds";

    public static final String FUNC_GETOWNER = "getOwner";

    public static final String FUNC_ADDFUNDS = "addFunds";

    public static final String FUNC_SETWALLET = "setWallet";

    public static final String FUNC_CONTAINS = "contains";

    public static final String FUNC_SENDFUNDS = "sendFunds";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final Event FUNDSADDED_EVENT = new Event("FundsAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERCOMPLETED_EVENT = new Event("TransferCompleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERFAILED_EVENT = new Event("TransferFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TRANSFERINITIATED_EVENT = new Event("TransferInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event GASNEEDED_EVENT = new Event("gasNeeded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Bank(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Bank(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Bank(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Bank(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<FundsAddedEventResponse> getFundsAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDSADDED_EVENT, transactionReceipt);
        ArrayList<FundsAddedEventResponse> responses = new ArrayList<FundsAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundsAddedEventResponse typedResponse = new FundsAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FundsAddedEventResponse> fundsAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FundsAddedEventResponse>() {
            @Override
            public FundsAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDSADDED_EVENT, log);
                FundsAddedEventResponse typedResponse = new FundsAddedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FundsAddedEventResponse> fundsAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDSADDED_EVENT));
        return fundsAddedEventFlowable(filter);
    }

    public List<TransferCompletedEventResponse> getTransferCompletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERCOMPLETED_EVENT, transactionReceipt);
        ArrayList<TransferCompletedEventResponse> responses = new ArrayList<TransferCompletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferCompletedEventResponse typedResponse = new TransferCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferCompletedEventResponse> transferCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferCompletedEventResponse>() {
            @Override
            public TransferCompletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERCOMPLETED_EVENT, log);
                TransferCompletedEventResponse typedResponse = new TransferCompletedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferCompletedEventResponse> transferCompletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERCOMPLETED_EVENT));
        return transferCompletedEventFlowable(filter);
    }

    public List<TransferFailedEventResponse> getTransferFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERFAILED_EVENT, transactionReceipt);
        ArrayList<TransferFailedEventResponse> responses = new ArrayList<TransferFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferFailedEventResponse typedResponse = new TransferFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferFailedEventResponse> transferFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferFailedEventResponse>() {
            @Override
            public TransferFailedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERFAILED_EVENT, log);
                TransferFailedEventResponse typedResponse = new TransferFailedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferFailedEventResponse> transferFailedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERFAILED_EVENT));
        return transferFailedEventFlowable(filter);
    }

    public List<TransferInitiatedEventResponse> getTransferInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERINITIATED_EVENT, transactionReceipt);
        ArrayList<TransferInitiatedEventResponse> responses = new ArrayList<TransferInitiatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferInitiatedEventResponse typedResponse = new TransferInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferInitiatedEventResponse> transferInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferInitiatedEventResponse>() {
            @Override
            public TransferInitiatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERINITIATED_EVENT, log);
                TransferInitiatedEventResponse typedResponse = new TransferInitiatedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferInitiatedEventResponse> transferInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERINITIATED_EVENT));
        return transferInitiatedEventFlowable(filter);
    }

    public List<GasNeededEventResponse> getGasNeededEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(GASNEEDED_EVENT, transactionReceipt);
        ArrayList<GasNeededEventResponse> responses = new ArrayList<GasNeededEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GasNeededEventResponse typedResponse = new GasNeededEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.gas = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<GasNeededEventResponse> gasNeededEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, GasNeededEventResponse>() {
            @Override
            public GasNeededEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GASNEEDED_EVENT, log);
                GasNeededEventResponse typedResponse = new GasNeededEventResponse();
                typedResponse.log = log;
                typedResponse.gas = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<GasNeededEventResponse> gasNeededEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GASNEEDED_EVENT));
        return gasNeededEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> getBankFunds() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBANKFUNDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addFunds() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDFUNDS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setWallet(String _wallet) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETWALLET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_wallet)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> contains(String _wallet) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONTAINS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_wallet)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> sendFunds() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDFUNDS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Bank load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bank(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Bank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bank(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Bank load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Bank(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Bank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Bank(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Bank> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, String _receiver, String _uniqueId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_receiver), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueId)));
        return deployRemoteCall(Bank.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Bank> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, String _receiver, String _uniqueId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_receiver), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueId)));
        return deployRemoteCall(Bank.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Bank> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _receiver, String _uniqueId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_receiver), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueId)));
        return deployRemoteCall(Bank.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Bank> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _receiver, String _uniqueId) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_receiver), 
                new org.web3j.abi.datatypes.Utf8String(_uniqueId)));
        return deployRemoteCall(Bank.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class FundsAddedEventResponse extends BaseEventResponse {
        public String sender;

        public BigInteger amount;
    }

    public static class TransferCompletedEventResponse extends BaseEventResponse {
        public String sender;

        public String receiver;

        public BigInteger amount;
    }

    public static class TransferFailedEventResponse extends BaseEventResponse {
        public String sender;

        public String receiver;

        public BigInteger amount;

        public String reason;
    }

    public static class TransferInitiatedEventResponse extends BaseEventResponse {
        public String sender;

        public String receiver;

        public BigInteger amount;
    }

    public static class GasNeededEventResponse extends BaseEventResponse {
        public BigInteger gas;
    }
}
