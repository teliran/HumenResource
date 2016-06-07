package Backend;
public class Discount {
	private double quantity;
	private double disc;

	/////////////////
	///Constructor///
	/////////////////
	
	public Discount(double quantity, double disc)
	{
		this.quantity= quantity;
		this.disc= disc;
	}
	
	/////////////
	///Getters///
	/////////////
	
	public double getQun()
	{
		return quantity;
	}
	
	public double getDisc()
	{
		return disc;
	}


	
	
	/////////////
	///Setters///
	/////////////
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setDisc(double disc) {
		this.disc = disc;
	}
	
	//ToString
	public String toString(){
		String temp="quantity is: "+quantity+"\n"+
				"discount is: "+disc+"\n";
		return temp;
	}
}
