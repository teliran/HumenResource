package Backend;

import java.sql.Date;
import java.util.List;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public interface AgreementEntity {
	public String addVisit(String supId,Date date,Entity_BL en) throws AccessDeniedException;
	public String EditVisit(String supId,Date date, Entity_BL en,AgreementEntity sub,AgreementEntity old) throws AccessDeniedException;
	public void DeleteVisit(String supId, Date date, Entity_BL en) throws AccessDeniedException;
	public List<AgreementEntity> GetItAllVisit(String supId, Date date,Entity_BL entity_BL) throws AccessDeniedException;
}
