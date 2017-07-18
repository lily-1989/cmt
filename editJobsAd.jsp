<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_JobAd ad = null;
  try {
    ad = (CCI_JobAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_JobAd();
  }

%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Job Details</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Job Title:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "jobTitle" maxlength = "300" value = "<%= ad.getJobTitle() %>"/></span>
      <br><span class="edit-reqhelptext">(Careerbuilder.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Job Type Code:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "40" name = "jobTypeCode" maxlength = "40" value = "<%= ad.getJobTypeCode() %>"/></span>
      <br><span class="edit-reqhelptext">(Careerbuilder.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Industry Code 1:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "45" name = "industryCode1" maxlength = "45" value = "<%= ad.getIndustryCode1() %>"/></span>
      <br><span class="edit-reqhelptext">(Careerbuilder.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Industry Code 2:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "45" name = "industryCode2" maxlength = "45" value = "<%= ad.getIndustryCode2() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Industry Code 3:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "45" name = "industryCode3" maxlength = "45" value = "<%= ad.getIndustryCode3() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Work Type:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "workType" maxlength = "10" value = "<%= ad.getWorkType() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Job Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Job Location City:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "jobLocationCity" maxlength = "50" value = "<%= ad.getJobLocationCity() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Job Location State:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "jobLocationState" maxlength = "3" value = "<%= ad.getJobLocationState() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Job Location Zip:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "jobLocationZip" maxlength = "10" value = "<%= ad.getJobLocationZip() %>"/></span>
      <br><span class="edit-reqhelptext">(Careerbuilder.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Job Location Country:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "jobLocationCountry" maxlength = "50" value = "<%= ad.getJobLocationCountry() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Job Requirements</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Education:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "education" maxlength = "10" value = "<%= ad.getEducation() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Experience:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "experience" maxlength = "10" value = "<%= ad.getExperience() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Travel:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "15" name = "travel" maxlength = "15" value = "<%= ad.getTravel() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Compensation</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Base Pay Per:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "30" name = "basePayPer" maxlength = "30" value = "<%= ad.getBasePayPer() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Base Pay Low:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "30" name = "basePayLow" maxlength = "30" value = "<%= ad.getBasePayLow() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Base Pay High:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "30" name = "basePayHigh" maxlength = "30" value = "<%= ad.getBasePayHigh() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Other Pay:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "30" name = "otherPay" maxlength = "30" value = "<%= ad.getOtherPay() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Bonus:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "30" name = "bonus" maxlength = "30" value = "<%= ad.getBonus() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Commission:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "30" name = "commission" maxlength = "30" value = "<%= ad.getCommission() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Company Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Company Name:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "companyName" maxlength = "100" value = "<%= ad.getCompanyName() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Company URL:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "companyUrl" maxlength = "100" value = "<%= ad.getCompanyUrl() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Email for Enhanced Listing</span></th>
    </tr>
    <tr>
      <th th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Enhancement Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "enhanceEmail" maxlength = "100" value = "<%= ad.getEnhanceEmail() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional, Required for Flex Ads)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Job Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Name:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "contactName" maxlength = "200" value = "<%= ad.getContactName() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
    </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Fax:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "16" name = "contactFax" maxlength = "16" value = "<%= ad.getContactFax() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
    </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"> <span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Apply URL:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "applyUrl" maxlength = "100" value = "<%= ad.getApplyUrl() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Web ID</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Web ID:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "webID" maxlength = "20" value = "<%= ad.getWebID() %>"/></span>
      <br><span class="edit-helptext">(Careerbuilder.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Job Video</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Original Ad ID:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "originalAdID" maxlength = "20" value = "<%= ad.getOriginalAdID() %>"/></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Voice Type:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "voiceType" maxlength = "20" value = "<%= ad.getVoiceType() %>"/></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Company Video Provided:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "companyVideoProvided" maxlength = "10" value = "<%= ad.getCompanyVideoProvided() %>"/></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">MoreDetails URL:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "moreDetailsURL" maxlength = "200" value = "<%= ad.getMoreDetailsURL() %>"/></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Video Notes:</span></th>
      <td class="edit-bg1"><span class="edit-text"><textarea name = "videoNotes" rows = "5" cols = "40"><%= ad.getVideoNotes() %></textarea></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Video Search Keywords:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "videoSearchKeywords" maxlength = "200" value = "<%= ad.getVideoSearchKeywords() %>"/></span>
      <br><span class="edit-helptext">(Job Video - Optional)</span>
      </td>
    </tr>