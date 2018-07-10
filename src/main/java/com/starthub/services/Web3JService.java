package com.starthub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harrison on 5/14/2018.
 */
@Service
public class Web3JService {

    @Autowired
    private Web3j web3;

    @Value("${web3j.client-address}")
    private String etherClientAddress;

    public Web3JService() {
        web3 = Web3j.build(new HttpService(etherClientAddress));
    }

    public void subscribe() {
        web3.web3ClientVersion().observable().subscribe(x -> {
            System.out.println(x.getWeb3ClientVersion());
        });
    }

    public Map<String, BigInteger> transferFunds(Credentials credentials, double amount, String recipientAddress) throws Exception {
        TransactionReceipt receipt = Transfer.sendFundsAsync(web3, credentials, recipientAddress,
                BigDecimal.valueOf(amount), Convert.Unit.ETHER).get();
        Map<String, BigInteger> result = new HashMap<>();
        result.put(receipt.getTransactionHash(), receipt.getBlockNumber());
        return result;
    }

    public BigInteger getBalance(String address) throws Exception {
        return web3
                .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get().getBalance();
    }

    public Credentials getCredentials(String pathToWallet, String password) throws Exception {
        return WalletUtils.loadCredentials(password, pathToWallet);
    }

    public String createWallet(String pathToWallet, String password) throws Exception {
        return WalletUtils.generateFullNewWalletFile(password, new File(pathToWallet));
    }

    public void disconnect() {
        web3.web3ClientVersion().observable().doOnUnsubscribe(() -> {
            System.out.println("unsubscribed from network");
        });
    }
}
