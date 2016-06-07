package Backend;

import java.text.ParseException;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public interface IdSearchable {
	public IdSearchable SearchByIdVisit(String ID, Entity_BL en) throws AccessDeniedException, NumberFormatException, ParseException;
}
