package coin.tracker.zxr.utils;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

/**
 * Created by Mayur on 20-09-2017.
 */

public class CoinHelper {

    private static CoinHelper INSTANCE;
    private final String COIN_LIST = "coinList" ;
    private final String BTC = "BTC", ETH = "ETH", LTC = "LTC";
    private final String Bitcoin = "Bitcoin",
            Ethereum = "Ethereum",
            Litecoin = "Litecoin";


    // Prevent direct instantiation
    private CoinHelper() {

    }

    public static CoinHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoinHelper();
        }

        return INSTANCE;
    }

    /*
    * Initiate user coins with ETH, BTC, LTC
    * */
    public void init() {
        if (!Hawk.contains(BTC)) {
            addUserCoin(BTC, Bitcoin);
        }

        if (!Hawk.contains(ETH)) {
            addUserCoin(ETH, Ethereum);
        }

        if (!Hawk.contains(LTC)) {
            addUserCoin(LTC, Litecoin);
        }
    }

    /*
    * Get pre-populated coins
    * */
    public ArrayList<String> getInitialCoins() {
        ArrayList<String> initialCoins = new ArrayList<>();
        initialCoins.add(BTC);
        initialCoins.add(ETH);
        initialCoins.add(LTC);

        return initialCoins;
    }

    /*
    * Get a coin name based on coinsymbol
    * */
    public String getCoinName(String symbol) {
        String coinName = null;

        if (Hawk.contains(symbol)) {
            coinName = Hawk.get(symbol);
        }

        return coinName;
    }

    /*
    * Get all save coins
    * */
    public ArrayList<String> getAllUserCoins() {
        return Hawk.get(COIN_LIST, new ArrayList<String>());
    }

    /*
    * Add a coin to list of user tracked coin-symbols
    * */
    private void addCoinToList(String symbol) {
        ArrayList<String> coins = Hawk.get(COIN_LIST, new ArrayList<String>());

        if (!coins.contains(symbol)) {
            coins.add(symbol);
        }

        Hawk.put(COIN_LIST, coins);
    }

    /*
    * Add a coin to list of user tracked coin-symbols
    * */
    public void deleteCoinFromList(String symbol) {
        ArrayList<String> coins = Hawk.get(COIN_LIST, new ArrayList<String>());

        if (!coins.contains(symbol)) {
            coins.remove(symbol);
            Hawk.put(COIN_LIST, coins);
        }
    }

    /*
    * Save a coin with key as Symbol and value as name
    * And add the symbol to list
    * */
    public void addUserCoin(String symbol, String coinName) {
        Hawk.put(symbol, coinName);
        addCoinToList(symbol);
    }

    /*
    * Remove a tracked coin based on symbol
    * */
    public void deleteCoin(String symbol) {
        Hawk.delete(symbol);
        deleteCoinFromList(symbol);
    }
}
