public class Market //Where a stocklist will be stored in
{
  private Stock[] stock;
  
  public Market(){
    stock = null;
  }
  public Market(Stock[] s){
    stock = s;
  }
  
  public Stock[] getStock(){
    return stock;
  }
  
  public void showStockDetails(){
    for (Stock stockDetails: stock)
    System.out.println(stockDetails);
    System.out.println();
  } 
}