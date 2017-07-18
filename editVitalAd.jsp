<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_VitalAd ad = null;
  try {
    ad = (CCI_VitalAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_VitalAd();
  }

%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Obituary Details</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">First Name:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "firstName" maxlength = "50" value = "<%= ad.getFirstName() %>"/></span>
      <br><span class="edit-reqhelptext">(Legacy.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Last Name:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "lastName" maxlength = "50" value = "<%= ad.getLastName() %>"/></span>
      <br><span class="edit-reqhelptext">(Legacy.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Date of Birth:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "dateBirth" maxlength = "20" value = "<%= ad.getDateBirth() %>"/></span>
      <br><span class="edit-helptext">(Legacy.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Date of Death:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "dateDeath" maxlength = "20" value = "<%= ad.getDateDeath() %>"/></span>
      <br><span class="edit-helptext">(Legacy.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Age:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "age" maxlength = "20" value = "<%= ad.getAge() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Residence</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Neighborhood:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "neighborhood" maxlength = "100" value = "<%= ad.getNeighborhood() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">City:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "city" maxlength = "50" value = "<%= ad.getCity() %>"/></span>
      <br><span class="edit-helptext">(Legacy.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">State:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "3" name = "state" maxlength = "3" value = "<%= ad.getState() %>"/></span>
      <br><span class="edit-helptext">(Legacy.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Zip:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "zip" maxlength = "10" value = "<%= ad.getZip() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Funeral Home Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Funeral Home:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "funeralHome" maxlength = "100" value = "<%= ad.getFuneralHome() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
