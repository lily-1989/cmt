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
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Pet Description</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Type:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "type" maxlength = "50" value = "<%= ad.getType() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Color:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "color" maxlength = "50" value = "<%= ad.getColor() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Breed:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "breed" maxlength = "50" value = "<%= ad.getBreed() %>"/></span>
      <br><span class="edit-helptext">(Legacy Pets - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Gender:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "gender" maxlength = "10" value = "<%= ad.getGender() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Zip:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "zip" maxlength = "10" value = "<%= ad.getZip() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Age:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "age" maxlength = "20" value = "<%= ad.getAge() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Price:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice()%>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
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
      <br><span class="edit-helptext">(Legacy Pets - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Pet Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"> <span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Advertiser URL:</span></th>
      <td class="edit-bg1"> <span class="edit-text"><input type = "text" size = "50" name = "advertiserUrl" maxlength = "50" value = "<%= ad.getAdvertiserUrl() %>"/></span></td>
    </tr>





