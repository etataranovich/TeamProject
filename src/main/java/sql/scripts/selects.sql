ALTER TABLE itcompany_has_employee DROP PRIMARY KEY;
ALTER TABLE itcompany_has_employee ADD id INT NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE itcompany_has_employee ADD salary INT;
ALTER TABLE itcompany_has_employee MODIFY salary DOUBLE;

SELECT itcompany.name as company_name, employee.name,employee.last_name,itcompany_has_employee.salary
FROM itcompany_has_employee
LEFT JOIN employee ON itcompany_has_employee.Employee_id=employee.id
LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id
WHERE itcompany_has_employee.salary>12000.00
ORDER BY itcompany.name;

SELECT itcompany.name as company_name, employee.name,employee.last_name,itcompany_has_employee.salary
FROM itcompany_has_employee
LEFT JOIN employee ON itcompany_has_employee.Employee_id=employee.id
LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id
WHERE itcompany.name='Playtika'
ORDER BY itcompany.name;

SELECT itcompany.name as company_name,address.street, address.house,city.name as city_name, country.name as country_name
FROM itcompany
LEFT JOIN office ON office.ITcompany_id = itcompany.id
LEFT JOIN address ON address.id = office.adress_id
LEFT JOIN city ON city.id=address.City_id
LEFT JOIN country ON country.id=city.Country_id
WHERE address.street='Platonova'
ORDER BY itcompany.name;

SELECT itcompany.name as company_name,SUM(itcompany_has_employee.salary)
FROM itcompany_has_employee
LEFT JOIN employee ON itcompany_has_employee.Employee_id=employee.id
LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id
GROUP BY itcompany.id
ORDER BY SUM(itcompany_has_employee.salary) desc;

SELECT employee.id, employee.name,employee.last_name, SUM(itcompany_has_employee.salary) 
FROM itcompany_has_employee
LEFT JOIN employee ON itcompany_has_employee.Employee_id=employee.id
LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id
GROUP BY employee.id
HAVING SUM(itcompany_has_employee.salary)>3000.00
ORDER BY SUM(itcompany_has_employee.salary) DESC;

SELECT  employee.id,employee.name,employee.last_name,developers.project
FROM developers
LEFT JOIN employee ON employee.id=developers.Employee_id
ORDER BY employee.id;

SELECT  employee.id,employee.name,employee.last_name,economists.analize_type
FROM economists
LEFT JOIN employee ON employee.id=economists.Employee_id
ORDER BY employee.id;

SELECT  employee.id,employee.name,employee.last_name
FROM owner
LEFT JOIN employee ON employee.id=owner.Employee_id
ORDER BY employee.id;

SELECT  employee.id,employee.name,employee.last_name,sales.client_count
FROM sales
LEFT JOIN employee ON employee.id=sales.Employee_id
ORDER BY employee.id;