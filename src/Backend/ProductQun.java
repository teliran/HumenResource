package Backend;
public class ProductQun 
{
	private Product product;
	private double quantity;

	/////////////////
	///Constructor///
	/////////////////
	
	public ProductQun(Product product, double quantity)
	{
		this.quantity= quantity;
		this.product= product;
	}
	
	/////////////
	///Getters///
	/////////////
	
	public Product getPro()
	{
		return product;
	}
	
	public double getQun()
	{
		return quantity;
	}
	
	/////////////
	///Setters///
	/////////////
	
	public void setQun(double quantity)
	{
		this.quantity= quantity;		
	}
	
	
	public void setPro(Product product){
		this.product= product;
	}
	//////////////
	///ToString///
	//////////////
	
	public String toString(){
	return "quantity: "+quantity+"\n"
			+product.toString()+"\n";
	}
}