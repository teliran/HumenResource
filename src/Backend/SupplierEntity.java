package Backend;


import BL.Entity_BL;
import DAL.AccessDeniedException;

public interface SupplierEntity {
	public String addVisit(String supId,Entity_BL en) throws AccessDeniedException;
	public String EditVisit(String supId,Entity_BL en,SupplierEntity sub,SupplierEntity old) throws AccessDeniedException;
	public void DeleteVisit(String supId,Entity_BL en) throws AccessDeniedException;
}
