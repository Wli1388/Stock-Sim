public class Stock
{
  private String name;
  private int price;
  private int shares;
  private static int stocks;
  //Stock Constructors
  public Stock(){
    name = "";
    price = 0;
    shares = 0;
    stocks ++;
  }
  public Stock(String n, int p, int s){
    name = n;
    price = p;
    shares = s;
    stocks++;
  }
  //getters
  public String getName(){
    return name;
  }
  public int getPrice(){
    return price;
  }
  public int getShares(){
    return shares;
  }
  public static int getStocks(){
    return stocks;
  }
  //setters
  public void randomizePrice(){
    int changedPrice;
    boolean delisted = false;
    if(Math.random()*100 >= 50)
      changedPrice = (int)((Math.random() *42 * this.price)/115);
    else
      changedPrice = (int)(Math.random() * (-42) * this.price /115);
    /**Checks if the price of the stock is/will be 0.
     *If true: stock will act as delisted and price will always be 0 
     */
    if(price ==0 || price + changedPrice <= 0 || delisted){
      this.price = 0;
      this.shares = 0;
      delisted = true;
    }
    else
      changePrice(changedPrice);
  }
  private void changePrice(int p){
    price += p;
  }
  
  public void setShares(int s){
    shares = s;
  }
  public void changeShares(int s){
    shares += s;
  }
  
  public String toString(){
    return name + " stock costs $" + price + " per share with " + shares + " shares available";
  }
  
}