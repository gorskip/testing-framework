<html>
<head>
  <title>Test Report</title>
</head>
<body>
<table style="width: 100%;">
<tr>
    <th></th>
    <th>Test name</th>
    <th>Status</th>
    <th>Expected</th>
    <th>Response<th>
    <th>Failure Message</th>
  </tr>
<#list results as result>
    <tr>
        <td class="block">${result_index + 1}.</td>
        <td class="block">${result.testCase.name}</td>
        <td class="block">${result.status}</td>
        <td class="block">${result.testCase.rest.expected}</td>
        <td class="block">???</td>
        <#if result.status == 'FAILURE'>
            <td class="block">
                <#if result.failureMessage??>
                    ${result.failureMessage}
                </#if>
            <td>
         <#else>
            <td class="block">...</td>
        </#if>
    </tr>
</#list>
</table>
</body>
</html>