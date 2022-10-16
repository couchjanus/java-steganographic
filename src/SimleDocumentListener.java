import javax.swing.event.*;

public interface SimleDocumentListener extends DocumentListener {
	void update(DocumentEvent e);
	@Override
	default void insertUpdate(DocumentEvent e) {
		update(e);
	}
	
	@Override
	default void removeUpdate(DocumentEvent e) {
		update(e);
	}
	
	@Override
	default void changedUpdate(DocumentEvent e) {
		update(e);
	}
}
