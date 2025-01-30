# bobcat
A minimal version of Tomcat

Building Bobcat
```bash
mvn package assembly:single
```

Running Bobcat

```bash
java bobcat.jar --home web --port 7500
```

Connecting to web service
```java
<%@page import="java.net.URI"        %>
<%@page import="java.net.URL"        %>
<%@page import="java.io.InputStream" %>
<%@page import="org.json.JSONArray"  %>
<%@page import="org.json.JSONObject" %>

<%
	String location = "https://api.coinbase.com/v2/prices/BTC-USD/spot";
	URL url = new URI(location).toURL();
	InputStream in = url.openStream();
	String buffer = "";
	while (true) {
		int k = in.read();
		if (k == -1) break;
		buffer += (char)k;
	}

	JSONObject root = new JSONObject(buffer);
	JSONObject data = (JSONObject)root.get("data");
	Object amount = data.get("amount");
	out.println(amount);
%>
```

