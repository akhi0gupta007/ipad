<%@page import="com.kam.User"%>
<%@page import="com.kam.UserRole"%>
	<g:each status="color" in="${userlist}" var="user">
		<div class="customerCompany <g:if test="${color%2==0 }">secondGridColor</g:if><g:else >thirdGridColor</g:else>">
			<ul class=" adminHead fullWidth fl userListHead list">
				<li class="paddingTop userName fl">${user.username }</li>
				<li class="paddingTop userName fl">
					<g:if test="${user.middleName}">
						${user.firstName+' '+user.middleName+' '+user.lastName }
					</g:if>
					<g:else>
						${user.firstName+' '+user.lastName }
					</g:else>
				</li>
				<li class="paddingTop width25percent fl">${user.email }</li>
				<li class="paddingTop userName fl">${user.mobile }</li>
				<li class="enable paddingTop fl">
					<g:if test="${UserRole.findWhere(user:user).role.authority=='ROLE_ADMIN'}">
						<g:message code="users.user.role.two"/>
					</g:if>
					<g:else>
						<g:message code="users.user.role.one"/>
					</g:else>
				</li>
				<li class="enable fl">
					<g:if test="${user.enabled}">
						<g:if test="${user.username!=loggedinUser.username }">
							<a href="#"  class="activeButton activeEditBtn EnableDisableButton fl">
								<g:message code="admin.disableAccount"></g:message>
							</a>
						</g:if>
						<g:else>
							<a href="#"  class="activeButton activeEditBtn fl">
								<g:message code="admin.disableAccount"></g:message>
							</a>
						</g:else>
					</g:if>
					<g:else>
						<a href="#" class="activeEditBtn disableButton EnableDisableButton fl" id="active">
							<g:message code="admin.enableAccount"></g:message>
						</a>
					</g:else>
				</li>
				<g:if test="${user.enabled}">
					<li class="enable edit fl"><a href="#" class="editButton activeEditBtn fl" onclick="usersForm('${user.username}','editCustomerUsers','cusUserEditName');"><g:message code="users.user.edit"/></a></li>
				</g:if>
				<g:else>
					<li class="enable edit fl"><a href="#" class="editButton activeEditBtn fl"><g:message code="users.user.edit"/></a></li>
				</g:else>
				<input type="hidden" id="userNameForList" value="${user.username}" />
				<g:if test="${user.username!=loggedinUser.username }">
					<li class="delete fr"><g:remoteLink controller="site" action="deleteUser" id="${user.id  }" beforeSend="comfirmUserDelete('${user.username }')" before="if(comfirmUserDelete('${user.username }')){" after="}"  onSuccess="deleteUser(data,'${user.id  }');"   class="activeEditBtn disableButton  fr marginRight">active button</g:remoteLink></li>
				</g:if>
				<g:else>
					<li class="delete fr"><a href="#"   class="activeEditBtn disableButton  fr marginRight">active button</a></li>
				</g:else>
			</ul>
		</div>
	</g:each>
				
<!-- pagination start -->  
   	<div id="pagination"> 
		<util:remotePaginate controller="site" action="${actionToSend }" id="paginate" total="${totalSize }" update="UsersList"  max="10" />
	</div>
<!-- pagination end --> 