package nxthemesEditor;

import java.io.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.nuxeo.ecm.core.rest.*;
import org.nuxeo.ecm.webengine.model.*;
import org.nuxeo.ecm.webengine.model.impl.*;
import org.nuxeo.ecm.webengine.model.exceptions.*;
import org.nuxeo.ecm.webengine.*;
import org.nuxeo.theme.*;
import org.nuxeo.theme.elements.*;
import org.nuxeo.theme.themes.*;
import org.nuxeo.theme.editor.*;

@WebModule(name="nxthemes-editor")

@Path("/nxthemes-editor")
@Produces(["text/html", "*/*"])
public class Main extends DefaultModule {

  @GET @POST
  @Path("perspectiveSelector")
  public Object renderPerspectiveSelector(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("perspectiveSelector.ftl").arg("path", path);
  }

  @GET @POST
  @Path("themeSelector")
  public Object renderThemeSelector(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("themeSelector.ftl").arg("current_theme_name", getCurrentThemeName(path)).arg("themes", getThemes(path)).arg("pages", getPages(path));
  }

  @GET @POST
  @Path("canvasModeSelector")
  public Object renderCanvasModeSelector(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("canvasModeSelector.ftl");
  }

  @GET @POST
  @Path("backToCanvas")
  public Object renderBackToCanvas(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("backToCanvas.ftl");
  }

  @GET @POST
  @Path("themeManager")
  public Object renderThemeManager(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("themeManager.ftl");
  }

  @GET @POST
  @Path("fragmentFactory")
  public Object renderFragmentFactory(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("fragmentFactory.ftl");
  }

  @GET @POST
  @Path("elementEditor")
  public Object renderElementEditor(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementEditor.ftl").arg("selected_element_id", getSelectedElementId());
  }

  @GET @POST
  @Path("elementDescription")
  public Object renderElementDescription(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementDescription.ftl");
  }

  @GET @POST
  @Path("elementPadding")
  public Object renderElementPadding(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementPadding.ftl");
  }

  @GET @POST
  @Path("elementProperties")
  public Object renderElementProperties(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementProperties.ftl");
  }

  @GET @POST
  @Path("elementStyle")
  public Object renderElementStyle(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementStyle.ftl");
  }

  @GET @POST
  @Path("elementWidget")
  public Object renderElementWidget(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementWidget.ftl");
  }

  @GET @POST
  @Path("elementVisibility")
  public Object renderElementVisibility(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("elementVisibility.ftl");
  }

  @GET @POST
  @Path("stylePicker")
  public Object renderStylePicker(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("stylePicker.ftl");
  }

  @GET @POST
  @Path("styleProperties")
  public Object renderStyleProperties(@QueryParam("org.nuxeo.theme.application.path") String path) {
    return getTemplate("styleProperties.ftl");
  }
  
  @GET @POST
  @Path("select_element")
  public void selectElement(@QueryParam("id") String id) {
    def ctx = WebEngine.getActiveContext();
    SessionManager.setSelectedElementId(ctx, id);
  }
   
  public static String getSelectedElementId() {
    def ctx = WebEngine.getActiveContext();
    return SessionManager.getSelectedElementId(ctx);
  }  
  
  public static String getDefaultTheme(String applicationPath) {
    return ThemeManager.getDefaultTheme(applicationPath);
  }

  public static String getCurrentThemeName(String applicationPath) {
    String defaultTheme = getDefaultTheme(applicationPath);
    def ctx = WebEngine.getActiveContext();
    String currentPagePath = ctx.getCookie("nxthemes.theme");
    if (currentPagePath == null) {
      currentPagePath = defaultTheme;
    }
    return currentPagePath.split("/")[0];
  }

  public static List<PageElement> getPages(String applicationPath) {
    ThemeManager themeManager = Manager.getThemeManager();
    def ctx = WebEngine.getActiveContext();
    String currentPagePath = ctx.getCookie("nxthemes.theme");
    String defaultTheme = getDefaultTheme(applicationPath);
    String defaultPageName = defaultTheme.split("/")[1];

    def pages = [];
    if (!currentPagePath || !currentPagePath.contains("/")) {
      currentPagePath = defaultTheme;
    }

    String currentThemeName = currentPagePath.split("/")[0];
    String currentPageName = currentPagePath.split("/")[1];
    ThemeElement currentTheme = themeManager.getThemeByName(currentThemeName);

    if (currentTheme == null) {
      return pages;
    }

    for (PageElement page : ThemeManager.getPagesOf(currentTheme)) {
      String pageName = page.getName();
      String link = String.format("%s/%s", currentThemeName, pageName);
      String className = pageName.equals(currentPageName) ? "selected" : "";
      if (defaultPageName.equals(pageName)) {
        className += " default";
      }
      pages.add(new PageInfo(pageName, link, className));
    }
    return pages;
  }

  public static List<ThemeElement> getThemes(String applicationPath) {
    String defaultTheme = getDefaultTheme(applicationPath);
    def themes = [];
    if (!defaultTheme.contains("/")) {
      return themes;
    }

    String defaultThemeName = defaultTheme.split("/")[0];
    String defaultPageName = defaultTheme.split("/")[1];
    String currentThemeName = getCurrentThemeName(applicationPath);

    for (themeName in Manager.getThemeManager().getThemeNames()) {
      String link = String.format("%s/%s", themeName, defaultPageName);
      String className = themeName.equals(currentThemeName) ? "selected" : "";
      if (link.equals(defaultThemeName)) {
        className += " default";
      }
      themes.add(new ThemeInfo(themeName, link, className));
    }
    return themes;
  }

}
