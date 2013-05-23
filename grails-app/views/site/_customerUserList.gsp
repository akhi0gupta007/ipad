<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>
<%@page import="com.kam.Role"%>
<%@page import="com.kam.Customer"%>
<g:each in="${customerlist}" var="customer">
	<g:if test="${customer.isDeleted!=true }">
    	<div class="customerEnableDisable">
    		<div class="customerCompany secondGridColor">
    			<ul class="companyName adminHead fullWidth fl">
    				<li class="width25percent name paddingTop fl">${customer.name}</li> <input type="hidden" id="userNameForList" value="${customer.name}" />
    				<li class=" fl email" ><a href="#" class="createCustomer" onclick="customersForm('${customer.name}','createAdminData','customerAdminName');"><span><g:message code="user.createUsers.message"/></span></a></li>
     				<li class="fl width25percent" ><a href="#" class="createCustomer" onclick="customersForm('${customer.name}','viewCustomerUsers','customerUserName');"><span><g:message code="view.users"/></span></a></li>
    				<li class="enable fl">
     					<g:if test="${customer.enabled}">
        					<a href="#"  class="activeEditBtn activeButton enableDisableCustomer fl">active button</a>
        				</g:if>
       					<g:else>
							<a href="#"  class="activeEditBtn disableButton enableDisableCustomer fl">active button</a>
       					</g:else>
					</li>
 					<g:if test="${customer.enabled }">
     					<li class="enable edit fl"><a href="#"  class="editButton activeEditBtn"  onclick="customersForm('${customer.name}','editCustomers','customerName');"> <g:message code="users.user.edit"/> </a></li>
     				</g:if>
     				<g:else>
          				<li class="enable edit fl"><a href="#"  class="editButton activeEditBtn">edit</a></li>
     				</g:else>
     				<li class="role fr" id="customer${customer.id }"><g:remoteLink controller="site" action="deleteCustomer" id="${customer.id  }" beforeSend="comfirmUserDelete('${customer.name }')" before="if(comfirmUserDelete('${customer.name }')){" after="}"  onSuccess="deleteCustomer(data,'customer${customer.id  }');"  class="activeEditBtn disableButton fr">active button</g:remoteLink></li>
    			</ul>
   			</div>
    		<g:if test="${User.findAllWhere(customer:customer).size()==0 }">
    			<div class="customerCompany thirdGridColor">
    				<ul class="adminHead fullWidth ">
     					<li class="company paddingTop fl"><g:message code="user.not.available"/> </li>
     				</ul>
   				</div>
    		</g:if>
    		<g:else>
     			<div class="customerCompany thirdGridColor">
    				<ul class="adminHead fullWidth">
     					<li class="paddingTop width25percent fl"><g:message code="users.user.name"/> </li>
     					<li class="paddingTop email fl"><g:message code="users.insert.email"/> </li>
     					<li class="paddingTop fl"><g:message code="users.Contact.Number"/></li>
     					<li class="deactive fl"></li>
     					<li class="edit fr"></li>
    				</ul>
    			</div>
    			<g:each status="color" in="${User.findAllWhere(customer:customer) }" var="admin">
     				<adminList:adminList admin="${admin}" color="${color }"/>
    			</g:each>
    		</g:else>
   		</div>
	</g:if>
</g:each>
	
<!-- pagination start -->  
  	<div id="pagination">
  		<util:remotePaginate controller="site" action="customers" id="paginate" total="${totalSize }" update="customersList"  max="10" />
   	</div>
<!-- pagination end --> 