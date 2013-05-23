<g:if test="${color=='0' }">
    <div class="customerCompany secondGridColor">
  </g:if>
  <g:else>
  <div class="customerCompany thirdGridColor">
  </g:else>
<ul class="  adminHead fullWidth  list">
<li class="width25percent paddingTop fl"><a class="changePointer" title="${name}">${username}</a> <input type="hidden" id="userNameForList" value="${username}" /></li>
					
					<li class="email paddingTop fl">${email}</li>
					<li class="width25percent paddingTop fl">${mobile}</li>
						
					<li class="enable fl"><g:if
											test="${enabled}">
											<a href="#"  class="activeButton activeEditBtn EnableDisableButton fl">
												<g:message code="admin.disableAccount"></g:message>
											</a>

										</g:if> <g:else>
											<a href="#"   class="activeEditBtn disableButton EnableDisableButton fl" id="active">
												<g:message code="admin.enableAccount"></g:message>
											</a>
										</g:else></li>
										<g:if test="${enabled}">
											<li class="enable edit fl"><a href="#" class="editButton activeEditBtn" onclick="usersForm('${username}','editCustomerUsers','cusUserEditName');">edit</a></li>
										</g:if>
										<g:else>
											<li class="enable edit fl"><a href="#" class="editButton activeEditBtn"><g:message code="users.user.edit"/></a></li>
					
										</g:else>
										<li class="role fr"><g:remoteLink controller="site" action="deleteUser" id="${id  }" beforeSend="comfirmUserDelete('${username }')" before="if(comfirmUserDelete('${username }')){" after="}"  onSuccess="deleteUser(data,'${id  }');"   class="activeEditBtn disableButton  fr">active button</g:remoteLink></li>
				</ul>
				</div>