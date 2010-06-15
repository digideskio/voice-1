package org.odk.voice.widgets;

import java.io.IOException;
import java.io.Writer;

import org.odk.voice.constants.VoiceAction;
import org.odk.voice.local.ResourceKeys;
import org.odk.voice.servlet.FormVxmlServlet;
import org.odk.voice.vxml.VxmlDocument;
import org.odk.voice.vxml.VxmlField;
import org.odk.voice.vxml.VxmlForm;
import org.odk.voice.vxml.VxmlUtils;

/**
 * Widget rendered when a survey is resumed.
 * @author alerer
 *
 */
public class FormResumeWidget extends WidgetBase {
 
  String formTitle;
  
  public FormResumeWidget(String formTitle) {
    this.formTitle = formTitle;
  }
  
  @Override
  public void getPromptVxml(Writer out) throws IOException {
    String grammar = VxmlUtils.createGrammar(new String[]{"1","2","9","7"}, 
        new String[]{VoiceAction.CURRENT_PROMPT.name(),
                     VoiceAction.RESTART_SESSION.name(),
                     VoiceAction.LANGUAGE_MENU.name(),
                     VoiceAction.ADMIN.name()});
    String filled = 
      VxmlUtils.createSubmit(FormVxmlServlet.ADDR, "action") + "\n";
    VxmlField field = createField("action", 
        createPrompt(String.format(getString(ResourceKeys.FORM_RECONNECT),formTitle)),
            grammar, filled);
    VxmlForm startForm = new VxmlForm("resume", field);
    new VxmlDocument(sessionid, startForm).write(out);
  }

}
