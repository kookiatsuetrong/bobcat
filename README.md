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

Connecting to Ollama
```java
<%@page import="java.net.URI"               %>
<%@page import="java.net.URL"               %>
<%@page import="java.net.HttpURLConnection" %>
<%@page import="java.io.InputStream"        %>
<%@page import="java.io.OutputStream"       %>
<%@page import="org.json.JSONArray"         %>
<%@page import="org.json.JSONObject"        %>

<%
	JSONObject detail = new JSONObject();
	detail.put("model", "deepseek-r1:1.5b");
	detail.put("prompt", "What is coffee");
	detail.put("stream", false);	
	
	URL url = new URI("http://localhost:11434/api/generate").toURL();
	HttpURLConnection http = (HttpURLConnection)url.openConnection();
	http.setRequestMethod("POST");
	http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	
	http.setDoOutput(true);
	OutputStream output = http.getOutputStream();
	output.write(detail.toString().getBytes());
	output.flush();
	output.close();
	
	InputStream in = http.getInputStream();
	String buffer = "";
	while (true) {
		int k = in.read();
		if (k == -1) break;
		buffer += (char)k;
	}

	JSONObject root = new JSONObject(buffer);
	Object result = root.get("response");
	out.println(result);
%>

```
