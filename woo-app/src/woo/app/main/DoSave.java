package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.FileOpenFailedException;
import woo.exceptions.MissingFileAssociationException;
import java.io.IOException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  private Input<String> _file;

  /** @param receiver */
  public DoSave(Storefront receiver) {
    super(Label.SAVE, receiver);
      _file = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try {
      if (_receiver.getFileName().equals("")) {
        _form.parse();
        _receiver.saveAs(_file.value());
      }
      _receiver.save();

    } catch (MissingFileAssociationException | IOException e) {
      FileOpenFailedException exception = new FileOpenFailedException(_file.value());
      exception.getMessage();
    }
  }
}
