package shoppingCartMVC;

/*an interface of the Controller class
* doAction implementation can be extended
 */

public interface CommandListener {

public void UpdateView() throws Exception;
public Object[] doAction(ActionCode actionCode, Object[] args) throws Exception;

}
