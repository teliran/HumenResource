package Backend;
import java.text.ParseException;
import java.util.List;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public interface Entity {
	public String addVisit(Entity_BL en) throws AccessDeniedException;
	public String EditVisit(Entity_BL en,Entity sub) throws AccessDeniedException;
	public void DeleteVisit(Entity_BL en) throws AccessDeniedException;
	public List<Entity> GetItAll(Entity_BL entity_BL) throws AccessDeniedException, ParseException;
}
