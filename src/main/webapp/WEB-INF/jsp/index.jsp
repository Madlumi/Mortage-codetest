<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Morty</title>
<style>
.center {
  margin: auto;
}
.html {
  max-width: 140ch;
  padding: 3em 1em;
  margin: auto;
  font-size: 1em;
}
</style>
</head>
<body>
	<div class="html">
	<form action="/" method="post">
		<label for="name">Name:</label>
		<input type="text" id="name" name="name" required>

		<label for="loanAmmount">Loan Ammount:</label>
		<input type="number" step="any" id="loanAmmount" name="loanAmmount" required>
			
		<label for="interesst">Interesst:</label>
		<input type="number" step="any" id="interesst" name="interesst" required>
		<label for="years">Years:</label>
		<input type="number" id="years" name="years" required>
		<input type="submit" label="Calculate" value="add">
		
			
	</form>
	<br>
	<br>
	
	<div class="center">
	${stringlist}
	</div></div>
</body>
</html>
