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
      <td align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Sale Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Address:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "address" maxlength = "200" value = "<%= ad.getAddress() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">City:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "city" maxlength = "50" value = "<%= ad.getCity() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">State:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "3" name = "state" maxlength = "3" value = "<%= ad.getState() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Zip:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "zip" maxlength = "10" value = "<%= ad.getZip() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Country:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "country" maxlength = "50" value = "<%= ad.getCountry() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <td align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Sale Date</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Event Date:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "30" name = "eventDate" maxlength = "30" value = "<%= ad.getEventDate() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <td align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Sale Time</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Event Start Time:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "eventStartTime" maxlength = "20" value = "<%= ad.getEventStartTime() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Event End Time:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "eventEndTime" maxlength = "20" value = "<%= ad.getEventEndTime() %>"/></span></td>
    </tr>