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
<body onload="bs_input_file('multiple');">
	<div class="container">
		<div class="col-md-8 col-md-offset-2">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="fileUpload">Invesco</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="fileUpload">Pdf Compare</a></li>
					<li class="active"><a href="#">Batch Pdf Compare</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
			      <li><a href="#">${loggedinuser}</a></li>
			    </ul>
			</div>
			</nav>
			<form method="POST" action="multiFileCompare" enctype="multipart/form-data" id="comparePDFForm">
				<div class="alert alert-success" id="successmsg" style="display:none;">File placed in the shared drive.</div>
				<div class="alert alert-danger" id="errormsg" style="display:none;">Error while comparing the file.</div>
				<c:if test="${(errormsg != null && errormsg != '') }"><div class="alert alert-danger">${errormsg}</div></c:if>
				<div class="form-group">
					<div class="input-group input-file" name="baseFile">
						<input type="text" class="form-control"	placeholder='Choose files...' /> <span class="input-group-btn">
							<button class="btn btn-default btn-choose" type="button">Choose</button>
						</span>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group input-file" name="compareFile">
						<input type="text" class="form-control"	placeholder='Choose files...' /> <span class="input-group-btn">
							<button class="btn btn-default btn-choose" type="button">Choose</button>
						</span>
					</div>
				</div>
				<div class="form-group">
					<span id="shareDriveLocationSpan">Shared Drive Path: ${sharedLocation}</span><span id="sharedFolderSpan"></span>
					<div class="input-group">
						<input type="text" name="folderName" id="folderName" class="form-control"	placeholder='Choose a folder name...' onkeyup="appendTextMulti();"/>
					</div>
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
					<button type="submit" class="btn btn-primary pull-right">Submit</button>
					<button type="reset" class="btn btn-danger">Reset</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>