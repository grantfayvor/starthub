package com.starthub.controllers;

import com.starthub.models.EtherWallet;
import com.starthub.services.Web3JService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

/**
 * Created by Harrison on 6/4/2018.
 */

@RequestMapping("/api/wallet")
@RestController
public class WalletController {

    @Autowired
    private Web3JService web3JService;

    @RequestMapping(value = "/transfer-funds", method = RequestMethod.POST)
    public Map<String, BigInteger> sendFunds(@RequestBody EtherWallet wallet, @RequestParam("amount") double amount,
                                             @RequestParam("recipient") String recipient) throws Exception {
        return web3JService.transferFunds(web3JService.getCredentials(wallet.getAddress(), wallet.getPassword()), amount, recipient);
    }

    @RequestMapping("/balance")
    public BigInteger getBalance(@RequestParam("address") String address) throws Exception {
        return web3JService.getBalance(address);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createWallet(@RequestBody EtherWallet wallet) throws Exception {
        return web3JService.createWallet(wallet.getPathToWallet(), wallet.getPassword());
    }
}
