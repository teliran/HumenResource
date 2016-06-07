package Backend;

import static org.junit.Assert.*;
import java.util.List;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JunitBackend {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ContactCopyConstructor() {
		Contact cont=new Contact("test", "test", 000000);
		Contact copyCont=new Contact(cont);
		assertEquals(cont.getFirstName(), copyCont.getFirstName());
		assertEquals(cont.getLastName(), copyCont.getLastName());
		assertEquals(cont.getPhoneNum(), copyCont.getPhoneNum());
	}
	
	@Test
	public void ProductCopyConstructor() {
		Discount dis=new Discount(0, 0);
		List<Discount> a=new LinkedList<Discount>();
		a.add(dis);
		Product pro=new Product("test","test", "test", "test", "test", 0, a);
		Product copyProduct=new Product(pro);
		assertEquals(pro.getArea(),copyProduct.getArea());
		assertEquals(pro.getCatNum(),copyProduct.getCatNum());
		assertEquals(pro.getManID(),copyProduct.getManID());
		assertEquals(pro.getName(),copyProduct.getName());
		assertEquals(pro.getPrice(),copyProduct.getPrice(),0);
		assertEquals(pro.getDisList(),copyProduct.getDisList());		
	}
	

	@Test
	public void ManufacturerCopyConstructor() {
		Manufacturer man=new Manufacturer("test","test");
		Manufacturer manOther=new Manufacturer(man);
		assertEquals(man.getID(),manOther.getID());
		assertEquals(man.getName(),manOther.getName());		
	}

	@Test
	public void GetOrderPriceTest() {
		List<Discount> discountList=new LinkedList<Discount>();
		Discount dis=new Discount(10, 50);
		discountList.add(dis);
		Product pro=new Product("test", "test","test", "test", "test", 100, discountList);
		List<ProductQun> productList=new LinkedList<ProductQun>();
		ProductQun proQ=new ProductQun(pro, 50);
		productList.add(proQ);
		Order order=new Order(0, "test", null,null, 0, productList);
		assertEquals(2500, order.getPrice(),0);
	}

}
