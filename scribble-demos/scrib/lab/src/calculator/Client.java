package calculator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.runtime.util.Buf;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.net.SocketChannelEndpoint;

import calculator.EProtocol.Calc.Calc;
import calculator.EProtocol.Calc.statechans.C.Calc_C_1;
import calculator.EProtocol.Calc.roles.C;

public class Client
{
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		Calc calculator = new Calc();
		try (MPSTEndpoint<Calc, C> se = new MPSTEndpoint<>(calculator, Calc.C, new ObjectStreamFormatter()))
		{
			se.request(Calc.S, SocketChannelEndpoint::new, "localhost", 8888);
			
			Calc_C_1 s1 = new Calc_C_1(se);
			
			// todo: Implement the rest ...
			
			System.out.println("Done:");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
