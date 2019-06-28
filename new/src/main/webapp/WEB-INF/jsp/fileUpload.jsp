<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PDF Compare App</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" href="images/favicon.png" type="image/x-icon">
<script src="js/jquery-1.11.3.min.js?z=${cacheburst}"></script>
<script src="js/fileUpload.js?z=${cacheburst}"></script>
</head>
<body onload="bs_input_file();">
	<div class="container">
		<div class="col-md-8 col-md-offset-2">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="fileUpload">Invesco</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Pdf Compare</a></li>
					<li><a href="multiFileUpload">Batch Pdf Compare</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
			      <li><a href="#">${loggedinuser}</a></li>
			    </ul>
			</div>
			</nav>
			<form method="POST" action="#" enctype="multipart/form-data" id="comparePDFForm">
				<div class="alert alert-success" id="successmsg" style="display:none;">Compared file opened in the other tab.Please look at the pop blocker as well.</div>
				<div class="alert alert-success" id="successmsgsamecontent" style="display:none;">Both file have the same data.</div>
				<div class="alert alert-danger" id="errormsg" style="display:none;">Error while comparing the file.</div>
				<div class="alert alert-danger" id="errormsgsamecontent" style="display:none;"></div>
				<div class="form-group">
					<div class="input-group input-file" name="baseFile">
						<input type="text" class="form-control"	placeholder='Choose a file...' /> <span class="input-group-btn">
							<button class="btn btn-default btn-choose" type="button">Choose</button>
						</span>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group input-file" name="compareFile">
						<input type="text" class="form-control"	placeholder='Choose a file...' /> <span class="input-group-btn">
							<button class="btn btn-default btn-choose" type="button">Choose</button>
						</span>
					</div>
				</div>
				<div class="form-group">
					<label class="checkbox-inline">
				      <input type="checkbox" name="storeinshare" id="storeinshare" value="true" checked onchange="changeCheckBoxValue(this)">Store in Shared Drive 
				    </label> <span id="shareDriveLocationSpan">Path ${sharedLocation}</span><span id="sharedFolderSpan"></span>
				    <input type="text" class="form-control"	name="shareFolder" id="shareFolder" placeholder='folderlocation' onkeyup="appendText();"/>
				</div>
				<div class="form-group">
					<label class="checkbox-inline">
				      <input type="checkbox" name="matchCase" id="matchCase" value="false" onchange="changeCheckBoxValue(this)">Match case
				    </label>
				    <label class="checkbox-inline">
				      <input type="checkbox" name="compareTextStyles" id="compareTextStyles" value="false" onchange="changeCheckBoxValue(this)">Compare text styles
				    </label>
				    <label class="checkbox-inline">
				      <input type="checkbox" name="compareDiffOnly" id="compareDiffOnly" value="false" onchange="changeCheckBoxValue(this)">Create comparison only if differences are found
				    </label>
				</div>
				<div class="form-group">
					<a id="fileNameClick" target="_blank" href=""></a>
					<button type="button" id="submitBtn" class="btn btn-primary pull-right" onclick="generatePDF()">Submit</button>
					<button type="reset" class="btn btn-danger">Reset</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>