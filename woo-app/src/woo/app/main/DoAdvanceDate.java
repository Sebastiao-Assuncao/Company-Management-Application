package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import pt.tecnico.po.ui.DialogException;
import woo.Storefront;

import woo.app.exceptions.InvalidDateException;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {

  private Input<Integer> _daysToAdvance;

  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _daysToAdvance = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_daysToAdvance.value()< 0) {
      throw new InvalidDateException(_daysToAdvance.value());
    }
    _receiver.advanceDate(_daysToAdvance.value());
  }
}
