package Backend;

import java.text.ParseException;
import java.util.List;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public interface SupIdSearchable {
	public List<SupIdSearchable> SearchBySupIdVisit(String ID,Entity_BL en) throws AccessDeniedException, ParseException;
}
