public class Character //Player Character
{
  private int netBalance;
  
  public void setNetBalance(int netBalance){
    this.netBalance = netBalance;
  }
  
  public int getNetBalance(){
    return netBalance;
  }
  public void changeNetBalance(int c){
    netBalance += c;
  }
}