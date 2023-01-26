package com.example.finalyearproject;

import static org.web3j.tx.gas.DefaultGasProvider.GAS_LIMIT;
import static org.web3j.tx.gas.DefaultGasProvider.GAS_PRICE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Text;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Convert;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.web3j.abi.datatypes.Function;
public class paymentActivity extends AppCompatActivity {
 Web3j web3;
 File file;
 String walletname;
 Credentials credentials;
 TextView txtaddress;
    EthGetBalance ethGetBalance;
    EthGasPrice gasPrice;
    String ethbalanceeth;
    String function;
    String answer;
    String a;
    String bigint;

    public paymentActivity() throws ExecutionException, InterruptedException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
         credentials = Credentials.create("7712f4f9952f61dba22107d1470979eccacfdd1c381f72db251624dc9d3984a4");

        web3 = Web3j.build(new HttpService("HTTP://192.168.0.127:7545"));
//change server address and portnumber


        ExecutorService es = Executors.newCachedThreadPool();


        es.execute(new Runnable() {
            @Override
            public void run() {


                //make new bank

                try {
                    String getFunds;
                    final BigInteger gasPrice = GAS_PRICE;
                    final BigInteger gasLimit = GAS_LIMIT;
                    BigDecimal b = Convert.toWei("1", Convert.Unit.ETHER);
                    final ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
                    TransactionManager manager = new RawTransactionManager(web3, credentials, 200, 500);
                    final BankFactory bfContract = BankFactory.load("0x99AfBcdB259C9b788919D7477Cd172d3A96E763b", web3, manager, gasProvider);
                    String a = String.valueOf(bfContract.createBank("0x5AC4302A4E4cc2Eb738042eE74253fe3c6f0f109", b.toBigInteger()).send());
                    getFunds = String.valueOf(bfContract.getBankDetails("0x5AC4302A4E4cc2Eb738042eE74253fe3c6f0f109").send());
                     bigint = String.valueOf(bfContract.getBank("0x5AC4302A4E4cc2Eb738042eE74253fe3c6f0f109").send()); // <-- throws exception
// <-- throws exception
// <-- throws exception

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //get bank funds
//                Bank contract = Bank.load(
//                        "0xF831729EF01a22A47c521Aa493ef1bccEF427a3A",
//                        web3,
//                        credentials,
//                        GAS_PRICE,
//                        GAS_LIMIT);
//
//                try {
//                    bigint = contract.getBankFunds().send();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
            }
        });
        es.shutdown();
        try {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        TextView ethgptv = findViewById(R.id.GaspriceTVdisplay);
//     //   ethgptv.setText(gasPrice.getGasPrice().toString());
        ethgptv.setText(bigint);



//        Function function1 = new Function(
//                "getBankDetails", // This is the name of the solidity function in your smart contract
//                Collections.emptyList(),  // Solidity Types in smart contract functions, no input in the function so we set this to empty
//                Arrays.asList(new TypeReference<Uint256>() {})); // result will be a string
//        String encodedFunction = FunctionEncoder.encode(function1);
//        EthCall ethCall = null;
//        try {
//            ethCall = web3.ethCall(
//                            Transaction.createEthCallTransaction(credentials.getAddress(), "0x9712aA9c5d83d4507fCF1f82Bb483cb2F154b6dc", encodedFunction),
//                            DefaultBlockParameterName.LATEST)
//                    .sendAsync().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }

//        String value = ethCall.getValue();
//        List<Type> list = FunctionReturnDecoder.decode(value, function1.getOutputParameters());
//
//        System.out.println(list.size());
        //contract.getBankFunds().call({from:"0xA1130115979522C328e2F90EbD816F4b7CCCEF21"});


//        TextView ethbalanceTV = findViewById(R.id.balanceOfaccountweiTVdisplay);
//        ethbalanceTV.setText(ethGetBalance.getBalance().toString());
//
//        TextView ethbalanceethTV = findViewById(R.id.balanceofaccountethTVdisplay);
//        ethbalanceethTV.setText(ethbalanceeth);
    }


