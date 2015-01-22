package CEPService_pkg;

import java.rmi.RemoteException;

import org.apache.axis.AxisFault;

public class Main {

	public static void main(String[] args) {
		
		try {
			CEPServiceBindingStub stub = new CEPServiceBindingStub();
			String retorno = stub.obterLogradouroAuth("85502070", "ladair", "N76tJL4");
			
			System.out.println(retorno);
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
