package control.viewController;

import javafx.fxml.FXML;
import view.bibComponent.EntryTextField;

/**
 * Abstract superclass for any publication that refers to a technical document. <br>
 * The following controllers directly inherit the
 * AbstractConferenceController:<br>
 * <ul>
 * <li>{@link ManualController}</li>
 * <li>{@link TechreportController}</li>
 * </ul>
 * 
 * @author Miklas Boskamp
 */
public abstract class AbstractTechnicalDocumentController extends AbstractPrintEntryController {

	@FXML
	public EntryTextField address;
	
	@Override
	public abstract void initialize();
}
