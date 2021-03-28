<%@taglib prefix = "c"  uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Style/css/components.css">
        <link rel="stylesheet" href="Style/css/icons.css">
        <link rel="stylesheet" href="Style/css/responsee.css">
        <title>TJEE-Projekt</title>
    </head>
    <body>
        <div class="s-12 l-6 center text-center"><h2><b>TJEE Video Player</b></h2></div><hr/>
        <div class="s-12 l-12 grid margin center">
            <div class="s-12 l-6">
                 <div class="s-12 l-10 center">
                    <form  class="customform" action="AddFilm" method="post" enctype="multipart/form-data">
                        <h3><b>Upload Video</b></h3>
                        <div class="s-12 l-4"><input required name="file" title="Choose ..." type="file" accept="video/mp4"/></div>
                        <div class="s-12 l-2 right"><button type="submit"> Upload </button></div>
                    </form>
                 </div>
            </div>
            <div class="s-12 l-6">
                        <video  class="center"width="640" height="360" autoplay controls poster="poster.jpg">
                            <c:if test="${cont != null}">
                                <source  type="video/mp4" src="data:video/mp4;base64,${cont}"/>
                            </c:if>
                        </video>   
            </div>
        </div><hr/>
        <div class="s-12 l-12 center">
            <h3><b>Uploaded videos</b></h3>
                <table>
                    <thead>
                        <tr>
                            <th><b>Name</b></th>
                            <th width="300"><b>Date Add</b></th>
                            <th width="400"><b>Actions</b></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- start web service invocation --%>
                        <%
                        try {
                            ws_clients.DataAccessSOAP_Service service = new ws_clients.DataAccessSOAP_Service();
                            ws_clients.DataAccessSOAP port = service.getDataAccessSOAPPort();
                            // TODO process result here
                            java.lang.String result = port.showFilmList();
                            out.println(result);
                        } catch (Exception ex) {
                            // TODO handle custom exceptions here
                        }
                        %>
                        <%-- end web service invocation --%>
                    </tbody>
                </table>
        </div>
    </body>
</html>
