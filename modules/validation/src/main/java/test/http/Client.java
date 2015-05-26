package test.http;

import java.io.IOException;

import org.scribble2.net.Buff;
import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.util.ScribbleRuntimeException;

import test.http.message.AcceptRanges;
import test.http.message.Body;
import test.http.message.CRLF;
import test.http.message.ContentLength;
import test.http.message.ContentType;
import test.http.message.Date;
import test.http.message.ETag;
import test.http.message.Host;
import test.http.message.HttpMessageFormatter;
import test.http.message.HttpVersion;
import test.http.message.LastModified;
import test.http.message.RequestLine;
import test.http.message.Server;
import test.http.message.Vary;
import test.http.message.Via;
import test.http.message._200;
import test.http.message._404;

public class Client
{
	public Client()
	{

	}

	public static void main(String[] args) throws ScribbleRuntimeException
	{
		Buff<HttpVersion> b_vers = new Buff<>();
		Buff<AcceptRanges> b_acc = new Buff<>();
		Buff<ContentLength> b_clen = new Buff<>();
		Buff<ContentType> b_ctype = new Buff<>();
		Buff<Body> b_body = new Buff<>();
		Buff<Date> b_date = new Buff<>();
		Buff<ETag> b_etag = new Buff<>();
		Buff<LastModified> b_lastm = new Buff<>();
		Buff<Server> b_serv = new Buff<>();
		Buff<Vary> b_vary = new Buff<>();
		Buff<Via> b_via = new Buff<>();
		Buff<_200> b_200 = new Buff<>();
		Buff<_404> b_404 = new Buff<>();
		
		Http http = new Http();
		SessionEndpoint se = http.project(Http.C, new HttpMessageFormatter());
		
		String host = "www.doc.ic.ac.uk";
		
		try (Http_C_0 init = new Http_C_0(se))
		{
			init.connect(Http.S, host, 80);
			Http_C_1 s1 = init.init();

			Http_C_2 s2 = s1.send(new RequestLine("/~rhu/", "1.1"));
			s2 = s2.send(new Host(host));
			Http_C_3 s3 = s2.send(new CRLF());
			Http_C_4 s4 = s3.receive(Http.HTTPV, b_vers);
			X: while (true)
			{
				Http_C_6 s6 = s4.branch();
				switch (s6.op)
				{
					case ACCEPTR:
					{
						s4 = s6.receive(Http.ACCEPTR, b_acc);
						break;
					}
					case BODY:
					{
						Http_C_5 s5 = s6.receive(Http.BODY, b_body);
						System.out.println(b_body.val.getBody());
						s5.end();
						break X;
					}
					case CONTENTL:
					{
						s4 = s6.receive(Http.CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s4 = s6.receive(Http.CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s4 = s6.receive(Http.DATE, b_date);
						break;
					}
					case ETAG:
					{
						s4 = s6.receive(Http.ETAG, b_etag);
						break;
					}
					case LASTM:
					{
						s4 = s6.receive(Http.LASTM, b_lastm);
						break;
					}
					case SERVER:
					{
						s4 = s6.receive(Http.SERVER, b_serv);
						break;
					}
					case VARY:
					{
						s4 = s6.receive(Http.VARY, b_vary);
						break;
					}
					case VIA:
					{
						s4 = s6.receive(Http.VIA, b_via);
						break;
					}
					case _200:
					{
						s4 = s6.receive(Http._200, b_200);
						break;
					}
					case _404:
					{
						s4 = s6.receive(Http._404, b_404);
						break;
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
		{
			e.printStackTrace();
		}
	}
}
