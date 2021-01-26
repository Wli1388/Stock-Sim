import java.util.*;

public class Backstage{ //Where the bulk of the code will be
  private static int goal = 0;
  private static int inventoryPrice[] = new int[10];
  private static int inventoryShares[] = new int[10];
  private static String inventoryName[] = new String[10];
  private static int amountOfShares = 0;
  private static int stockPosition = 0;
  private static String targetedStock = "";
  private static Market stockMarket = new Market();
  private static Character player = new Character();
  
  public static void characterMoneyCreation(){
    player.setNetBalance((int)(Math.random()*1000) + 10000); 
  }
  public static void introduction() throws InterruptedException{
    System.out.println("Welcome to StockSim! StockSim is a virtual stock market where you invest virtual money in hopes of gaining more money." +
            "\nYour goal is to earn $1250 in 5 days. All stocks will disappear in 5 days, so make sure to sell all of the stocks you have by then!");
    characterMoneyCreation();
    goal = player.getNetBalance() + 1250;
    Thread.sleep(15000);
  }
  public static void conclusion() throws InterruptedException{
    Scanner conclusion = new Scanner(System.in);
    System.out.println("It is now the 6th day. All stocks have disappeared and any stocks you have in your possession is worthless.");
    Thread.sleep(6000);
    if(player.getNetBalance() >= goal)
      System.out.println("Congrats! You had made a profit of $1250+. Be proud that you trade smart and RNGesus is on your side.");
    else if(player.getNetBalance() < goal -1000)
      System.out.println("Not only did you not earn any profit, but you also lost money! Be glad that this is a virtual market.");
    else
      System.out.println("While you did not reach your goal of earning $1250, you still made some profit. So well done!");
    Thread.sleep(6250); 
    System.out.println("Do you want to travel back in time and play the market again? Do note that prices will be different from last time." +
                       "\nYes | No");
    String choice = conclusion.next();
    if (choice.equalsIgnoreCase("Yes")){
      characterMoneyCreation();
      dailyActivity();
      conclusion();
    }
    else
      System.out.println("Goodbye then.");
    Thread.sleep(1000);
  }

  //Method for initializing stocks
  public static void stockCreation() throws InterruptedException{  
    Stock spritee = new Stock("Spritee", 35, 20310);
    Stock noAIs = new Stock("NoAIs", 311, 1900);
    Stock blizzad = new Stock("Blizzad", 65, 12420);
    Stock vacForMac = new Stock ("VacForMac", 20, 500);
    Stock lifeHijacker = new Stock ("LifeHijacker", 197, 8500);
    Stock activisionTronics = new Stock ("ActivisionTronics", 69, 1500);
    Stock nonEmpire = new Stock("NonEmpire", 70, 2000);
    Stock oMechaInd = new Stock("OMechaIndustry", 465, 140);
    Stock regionEnergy = new Stock("RegionEnergy", 132, 13500);
    Stock burgerMonopoly = new Stock("BurgerMonopoly", 142, 96500);
    
    Stock[] stockList = {spritee, noAIs, blizzad, vacForMac, lifeHijacker, activisionTronics, nonEmpire, oMechaInd, regionEnergy, burgerMonopoly};
    stockMarket = new Market(stockList);
  }

  public static void dailyActivity() throws InterruptedException{
    stockCreation();
    for (int day = 1; day <= 5; day++){
      System.out.println("It is day " + day +"\n");
      for(int position = 0; position < stockMarket.getStock().length; position++){ //Changes the market prices every day for 5 days
        stockMarket.getStock()[position].randomizePrice();
      }
      userInteract();
    }
  }
  //Main Component of this program. Houses all the user interaction methods
  public static void userInteract() throws InterruptedException{
    Scanner scan1 = new Scanner(System.in);
    System.out.println("Here are the stocks, displayed with the price and number of shares available: \n \n");
      
    stockMarket.showStockDetails();
    System.out.println("POSSESSED STOCKS");
    for(int j = 0; j < 10; j++){ //Lists the stocks in your inventory if you have more than 0 shares
      if(inventoryShares[j] > 0)
        System.out.println(inventoryName[j] +": Bought for the latest price of $" + inventoryPrice[j] + " per share with " +
                           inventoryShares[j] + " total shares.");
      }
    System.out.println();
    System.out.println("What would you like to do today?\nYou have $" + player.getNetBalance() + "\nBuy | Sell | Quick Sell | More Options");
    String option = scan1.nextLine();
    if (option.equalsIgnoreCase("Buy"))
      buyStocks();
    else if (option.equalsIgnoreCase("Sell"))
      sellStocks();
    else if (option.equalsIgnoreCase("Quick Sell"))
      sellAll();      
    else if (option.equalsIgnoreCase("More Options"))
      moreOptions();
    else
      userInteract();
  }
    
    
  //Method for buying stocks
  public static void buyStocks() throws InterruptedException{
    Scanner scan2 = new Scanner(System.in);
    boolean valid = false;
    System.out.println("What stock would you like to buy?");
    String boughtStock = "";
    while(!valid){
      boughtStock = scan2.next();
      for(int s = 0; s < stockMarket.getStock().length; s++){
        if(boughtStock.equalsIgnoreCase(stockMarket.getStock()[s].getName())){
          targetedStock = stockMarket.getStock()[s].getName();
          stockPosition = s;
          valid = true;
        }
      }
      if(!valid)
        System.out.println("Sorry, there is no stock from that name. Please try again.");
    }
    System.out.println(targetedStock + "? How many shares would you like to buy?");
    checkInputOfShares();
    if(stockMarket.getStock()[stockPosition].getPrice() * amountOfShares > player.getNetBalance() || amountOfShares < 0 
       || amountOfShares > stockMarket.getStock()[stockPosition].getShares()){ //Checks if valid
        System.out.println("Sorry, you either can't afford it, input a negative number, OR there's not enough shares in the market. Going back...\n");
        Thread.sleep(1000);
        userInteract();
    }
    else{
      player.changeNetBalance(-(stockMarket.getStock()[stockPosition].getPrice() * amountOfShares));
      inventoryName[stockPosition] = stockMarket.getStock()[stockPosition].getName();
      inventoryPrice[stockPosition] = stockMarket.getStock()[stockPosition].getPrice();
      inventoryShares[stockPosition] += amountOfShares;
      stockMarket.getStock()[stockPosition].changeShares(-amountOfShares);
      System.out.println("Stock bought successfully! Would you like to do something else today?" +
                         "\nYou have $" + player.getNetBalance() + " leftover" + "\nYes | No\n");
      String option = scan2.next();
      if(option.equalsIgnoreCase("Yes"))
        userInteract();
      else
        System.out.println("A no then. Alrighty then, you blacked out for 12 hours and you wake up to find its the next day\n");
    }
  }
  //Method for selling stocks
  public static void sellStocks() throws InterruptedException{
    Scanner scan3 = new Scanner(System.in);
    boolean valid = false;
    System.out.println("What stock would you like to sell?");
    String sellingStock = "";
    while(!valid){                           //Checks if there is a stock of that name in inventory 
      sellingStock = scan3.next();
      for(int s = 0; s < inventoryName.length; s++){
        if(sellingStock.equalsIgnoreCase(inventoryName[s]) && inventoryShares[s] > 0){
          targetedStock = inventoryName[s];
          stockPosition = s;
          valid = true;
        }
      }
      if(!valid){
        System.out.println("Sorry, you don't have any of that stock in your inventory. Please try again.\n");
      }
    }
    System.out.println(targetedStock + "? How many shares would you like to sell?");
    checkInputOfShares();
    if(amountOfShares > inventoryShares[stockPosition]){ //Checks if player has enough shares to sell
        System.out.println("Sorry, you don't have enough stocks to sell. Going back...\n");
        Thread.sleep(1000);
        userInteract();
    }
    else{
      player.changeNetBalance((stockMarket.getStock()[stockPosition].getPrice() * amountOfShares));
      inventoryShares[stockPosition] -= amountOfShares;
      stockMarket.getStock()[stockPosition].changeShares(amountOfShares);
      System.out.println("Stock sold successfully! Would you like to do something else today?" +
                         "\nYou have $" + player.getNetBalance() + " leftover" + "\nYes | No\n");
      String option = scan3.next();
      if(option.equalsIgnoreCase("Yes"))
        userInteract();
      else
        System.out.println("A NO then. Alrighty then, you blacked out for 12 hours and you wake up to find its the next day\n");
    }
  }
  //Method for selling all possessed shares 
  public static void sellAll() throws InterruptedException{
    Scanner scan3 = new Scanner(System.in);
    for (int i = 0; i < 10; i++){
      if(inventoryShares[i] > 0){
        player.changeNetBalance(stockMarket.getStock()[i].getPrice() * inventoryShares[i]);
        inventoryShares[i] = 0;
      }
    }
    System.out.println("Stocks sold successfully! Would you like to do something else today?" +
                       "\nYou have $" + player.getNetBalance() + " leftover" + "\nYes | No\n");
    String option = scan3.next();
    if(option.equalsIgnoreCase("Yes"))
      userInteract();
    else
      System.out.println("A NO then. Alrighty then, you blacked out for 12 hours and you wake up to find its the next day\n");
  }
  //Checks if the input is an int. For robustness
  public static void checkInputOfShares(){
    Scanner scan3 = new Scanner(System.in);
    try{
      amountOfShares = scan3.nextInt();
    }
    catch(Exception E)
    {
      System.out.println("Sorry, not a valid input. Please try again.");
      checkInputOfShares();
    }
  }
  //Method for more options. Does not impact gameplay
  public static void moreOptions() throws InterruptedException{
    Scanner scan4 = new Scanner(System.in);
    System.out.println("Average Stock Prices | Max Stock Price | Min Stock Price | Sum of Stock Price"
                         + " | Most Frequent Stock Price | Go Back");
    String choice = scan4.nextLine();
    if(choice.equalsIgnoreCase("Average Stock Prices")){
      int sum = 0;
      int average = 0;
      for(int i = 0; i < 10; i++)
        sum += stockMarket.getStock()[i].getPrice();
      average = (int)(sum/10);
      System.out.println("Average of $" + average + " per stock share.");
    }
    else if(choice.equalsIgnoreCase("Max Stock Price")){  
      int max = 0;
      int stockPos = 0;
      for (int i = 0; i < 10; i ++){
        if(stockMarket.getStock()[i].getPrice() > max){
          max = stockMarket.getStock()[i].getPrice();
          stockPos = i;
        }
      }
      System.out.println("Most expensive stock share is " + stockMarket.getStock()[stockPos].getName() +
                         " at $" + max + " per share.");
    }
    else if(choice.equalsIgnoreCase("Min Stock Price")){  
      int min = Integer.MAX_VALUE;
      int stockPos = 0;
      for (int i = 0; i < 10; i ++){
        if(stockMarket.getStock()[i].getPrice() < min){
          min = stockMarket.getStock()[i].getPrice();
          stockPos = i;
        }
      }
      System.out.println("Least expensive stock share is " + stockMarket.getStock()[stockPos].getName() +
                         " at $" + min + " per share.");
    }
    else if(choice.equalsIgnoreCase("Sum of Stock Prices")){
      int sum = 0;
      for(int i = 0; i < 10; i++)
        sum += stockMarket.getStock()[i].getPrice();
      System.out.println("Sum of 1 share for each stock is $" + sum);
    }
    else if(choice.equalsIgnoreCase("Most Frequent Stock Price")){
      int mode = 0;
      int currentNum = 0;
      int count1 = 0;
      int count2 = 0;
      for (int i = 0; i < 10; i++){
        currentNum = stockMarket.getStock()[i].getPrice();
        for (int j = 0+i; j <10; j++){
          if (currentNum == stockMarket.getStock()[j].getPrice())
            count1 ++;
        }
        if (count1 >= count2){
          mode = currentNum;
          count2 = count1;
        }
      }
      System.out.println("The most frequent stock price is $" + mode +" per share.");
    }
    else
      userInteract();
    userInteract();
  }
}