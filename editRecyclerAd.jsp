           <%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

           <%
               RecyclerAd ad = null;

               try {
                   ad = (RecyclerAd)session.getAttribute("ad");
               }
               catch (ClassCastException e) {
                   ad = new RecyclerAd();
               }
           %>


               <tr>
                  <th class="edit-bg1" align = "left" valign="top"><span class="edit-reqlabel">End Date:*</span></th>
                  <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "endDate" maxlength = "10" value = "<%= ad.getEndDate() == null ? "" : ad.getDateAsShortString(ad.getEndDate()) %>"/></span>
                      <A HREF="#" onClick="cal.select(document.forms['adSubmit'].endDate,'anchor.endDate','yyyy/MM/dd'); return false;" NAME="anchor.endDate" ID="anchor.endDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                      &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                  </td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Duration:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="6" name="duration"  maxlength = "6" value="<%= ad.getDuration() == null ? "" : "" + ad.getDuration() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Type:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "hidden" name = "type" value = "<%= ad.getType() %>"/>
                 <% if (new Integer(1).equals(ad.getType())) { %>
                     Web
                 <% } else if (new Integer(2).equals(ad.getType())){ %>
                     Print
                 <% } else { %>
                     N/A
                 <% } %>
                 </span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Ad Format:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "hidden" name = "adFormat" value = "<%= ad.getAdFormat() %>"/><%= ad.getAdFormat() == "" ? "N/A" : ad.getAdFormat() %></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Ad Layout:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type= "hidden" name="adLayout" value="<%= ad.getAdLayout() %>"/><%= ad.getAdLayout() == "" ? "N/A" : ad.getAdLayout() %></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Parent Class:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="8" name="parentClass" maxlength="8" value="<%= ad.getParentClass() %>"/></span></td>
               </tr>
               <tr>
                  <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Title:</span></th>
                  <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "title" maxlength = "300" value = "<%= ad.getTitle() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Insertions:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "insertions" maxlength = "3" value = "<%= ad.getInsertions() == null ? "" : "" + ad.getInsertions() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-reqlabel">Customer ID:*</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "38" name = "customerID" maxlength = "38" value = "<%= ad.getCustomerID() == null ? "" : "" + ad.getCustomerID() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Name:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="32" name="contactName"  maxlength = "32" value="<%= ad.getContactName() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Area Code:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "5" name = "areaCode" maxlength = "5" value = "<%= ad.getAreaCode() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Phone:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "16" name = "phone" maxlength = "16" value = "<%= ad.getPhone() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Additional Phone 1:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type="text" size="16" name="webPhone1"  maxlength = "16" value="<%= ad.getWebPhone1() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Additional Phone 2:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="16" name="webPhone2"  maxlength = "16" value="<%= ad.getWebPhone2() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Email:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "email" maxlength = "128" value = "<%= ad.getEmail() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">URL:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "url" maxlength = "500" value = "<%= ad.getUrl() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Location:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "location" maxlength = "100" value = "<%= ad.getLocation() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Zones:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="32" name="zones"  maxlength = "32" value="<%= ad.getZones() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Zip:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "zip" maxlength = "10" value = "<%= ad.getZip() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-reqlabel">Wanted:*</span></th>
                 <td class="edit-bg2"><span class="edit-text">
                 Yes <input type = "radio" name="wanted" value="1" <% if (new Integer(1).equals(ad.getWanted())) { %>checked<% } %> />
                 No <input type="radio" name="wanted" value="0" <% if (new Integer(0).equals(ad.getWanted())) { %>checked<% } %>/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Combo Ad:</span></th>
                 <td class="edit-bg1"><span class="edit-text">
                 Yes <input type = "radio" name="isOnline" value="1" <% if (new Integer(1).equals(ad.getIsOnline())) { %>checked<% } %> />
                 No <input type="radio" name="isOnline" value="0" <% if (new Integer(0).equals(ad.getIsOnline())) { %>checked<% } %>/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Cost:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "cost" maxlength = "10" value = "<%= ad.getCost() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Price:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "15" name = "price" maxlength = "15" value = "<%= ad.getPrice() == null ?  "" : "" + ad.getPrice() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Year:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "4" name = "year" maxlength = "4" value = "<%= ad.getYear() == null ? "" : "" + ad.getYear() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Make:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "32" name = "make" maxlength = "32" value = "<%= ad.getMake() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Model:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "32" name = "model" maxlength = "32" value = "<%= ad.getModel() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">VIN:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "25" name = "vin" maxlength = "25" value = "<%= ad.getVin() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Trim:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type="text" size="32" name="trim"  maxlength = "32" value="<%= ad.getTrim() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Mileage:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type="text" size="32" name="mileage"  maxlength = "32" value="<%= ad.getMileage() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Photo Name:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "photoName" maxlength = "200" value = "<%= ad.getPhotoName() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Photo Name 1:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "photoName1" maxlength = "200" value = "<%= ad.getPhotoName1() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Photo Name 2:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "photoName2" maxlength = "200" value = "<%= ad.getPhotoName2() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Photo Name 3:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "photoName3" maxlength = "200" value = "<%= ad.getPhotoName3() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Photo Name 4:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "photoName4" maxlength = "200" value = "<%= ad.getPhotoName4() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Photo Name 5:</span></th>
                 <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "photoName5" maxlength = "200" value = "<%= ad.getPhotoName5() %>"/></span></td>
               </tr>
               <tr>
                 <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Sort Text:</span></th>
                 <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "sortText" maxlength = "50" value = "<%= ad.getSortText() %>"/></span></td>
               </tr>
               <tr>
                  <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Web Content:</span></th>
                  <td class="edit-bg1"><textarea name = "content" rows = "15" cols = "40"><%= ((RecyclerAd)ad).getContent()%></textarea></td>
               </tr>
               <tr>
                  <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Print Text:</span></th>
                  <td class="edit-bg2"><textarea name = "extraText" rows = "15" cols = "40"><%= ((RecyclerAd)ad).getExtraText()%></textarea></td>
               </tr>