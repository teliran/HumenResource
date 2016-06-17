package transport;
import java.util.HashMap;
import java.util.List;

import Backend.Order;
import Backend.ProductQun;
import DB.DB;
public class OrderDocument {
	
	private int _docId;
	private String _dest;
	private String _source;
	//private HashMap<String, Integer> _supply;
	private List<ProductQun> _productsList;
	
	public OrderDocument(Order ord, String dest) {
		_source = ord.getSupNum();
		_dest = dest;
		_docId = TransManager.getDocId();
		_productsList = ord.getList();
		
	}
	
	public int get_docId() {
		return _docId;
	}
	public String get_dest() {
		return _dest;
	}
	public String get_source() {
		return _source;
	}
	public List<ProductQun> get_productsList() {
		return _productsList;
	}
	
	/*public void addSupply(String product, int amount){
		if (_supply.containsKey(product)){
			System.out.println("Product already entered");
		}
		else
			_supply.put(product, amount);
	}*/
	public void addDoc(int transId){
		for (ProductQun p: _productsList){
			String query ="INSERT INTO Doc_History (DocID, TransID, ProductID, ManufID ,Amount, SupplierId, Dest) " +
                "VALUES ("+_docId+", "+transId+", '"+p.getPro().getCatNum()+
                "', '"+p.getPro().getManID()+"', "+(int)(p.getQun())+", '"+_source+"' , '"+_dest+"');";
			DB.executeUpdate(query);
		}
	}
	
	
	/*private HashMap<String, Integer> initMap(){
		HashMap<String, Integer> ret = new HashMap<>();
		int leng = TransManager.get_productsArr().length;
		int numOfproduct = (int)(Math.random()*leng)+1;
		int memo[] = new int[leng];
		for (int i=0; i<numOfproduct; i++){
			int p =(int) (Math.random()*leng);
			while(memo[p]==1)
				p = (int) (Math.random()*leng);
			ret.put(TransManager.get_productsArr()[p], (int)(Math.random()*50)+1);
			memo[p]=1;
		}
		return ret;
	}*/

	
}
