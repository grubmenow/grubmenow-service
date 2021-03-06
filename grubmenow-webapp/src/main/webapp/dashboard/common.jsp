<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.grubmenow.service.dashboard.DashboardUtils"%>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/jquery/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="js/jqModal/jqModal.min.js" ></script>

<script type="text/javascript">

$(document).keyup(function(e) {
	   // esc
	   if (e.keyCode == 27) {

		   try{  
		  	$('#popUpJqmWindow').jqmHide();
		  }catch(err){}
	   }
	});
   
   	$(document).ready(function() {
   		$('.open_popup').click(function(e) {
   			 e.preventDefault();
   			 e.stopPropagation();
   			 var $link = $(this);
   			 $('#popUpJqmWindow').jqm({modal:true});
   			 $('#popUpJqmWindow').jqmShow();
   			 $('#popUpJqmWindow').html('<p align="center"><img src="img/loader.gif"></p>');
   			 $('#popUpJqmWindow').load($link.attr('href'));
   			 return false;
  		     });

   	});

</script>

<%
  if(!DashboardUtils.isDashboardAccess(request)) {
	  
	  // if the value is passed in the url
	  if(StringUtils.isNotBlank(request.getParameter("accesscode"))) {
          Cookie cookie = new Cookie("accesscode", request.getParameter("accesscode"));
          cookie.setMaxAge(24*60*60);
          response.addCookie(cookie);
          
          response.sendRedirect("console");
          return;
	  }
	  
	  response.sendRedirect("accesscode");
  	  return;
  }
%>