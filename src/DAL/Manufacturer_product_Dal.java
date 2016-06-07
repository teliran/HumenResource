package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Manufacturer_product_Dal {
	
	private Entity_Dal entity_dal;
	private Connection conn;

	protected Manufacturer_product_Dal(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}
	
	protected void DeleteManufacturerProduct(String productNum) throws AccessDeniedException
	{
		String query="DELETE FROM product_manufacturer WHERE Product_Num=? ";
		PreparedStatement pst=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, productNum);
			pst.execute();
			pst.close();		
			} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Delete Manufacturer Product.");
		}
	}

}
