package control.viewController;

import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;

import javafx.fxml.FXML;
import view.bibComponent.EntryTextField;

/**
 * Controller that handles the input of user data about a {@link BibTeXEntry#TYPE_BOOKLET booklet}.
 * 
 * @author Miklas Boskamp
 */
public class BookletController extends AbstractPrintEntryController {

	@FXML
	public EntryTextField howpublished;
	
	@FXML
	public EntryTextField address;
	
	@Override
	public void initialize() {
		// Do nothing
	}

	@Override
	public Key getEntryType() {
		return BibTeXEntry.TYPE_BOOKLET;
	}
}