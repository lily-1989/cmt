<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_GenAd ad = null;
  try {
    ad = (CCI_GenAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_GenAd();
  }
%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Celebration Details</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Celebration Type:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "celebrationType" maxlength = "50" value = "<%= ad.getCelebrationType() %>"/></span></td>
    </tr>
    <tr>
       <th colspan="2">&nbsp;</th>
     </tr>
     <tr>
       <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Participants</span></th>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">First Name1:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "firstName1" maxlength = "50" value = "<%= ad.getFirstName1() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Last Name1:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "lastName1" maxlength = "50" value = "<%= ad.getLastName1() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">First Name2:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "firstName2" maxlength = "50" value = "<%= ad.getFirstName2() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Last Name2:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "lastName2" maxlength = "50" value = "<%= ad.getLastName2() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
        <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Email for Enhanced Listing</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Enhancement Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "enhanceEmail" maxlength = "100" value = "<%= ad.getEnhanceEmail() %>"/></span>
    </tr>
    <tr>
          <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Celebration Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"> <span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span></td>
    </tr>





