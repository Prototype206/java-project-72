package gg.jte.generated.ondemand.urls;
import hexlet.code.model.Url;
import java.util.List;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,6,6,19,19,21,21,21,23,23,23,23,23,23,23,28,28,32,32,32,32,32,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, List<Url> urls) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <div class=\"container-lg mt-5\">\r\n            <h1>Сайты</h1>\r\n            <table class=\"table table-bordered table-hover mt-3\" data-test=\"urls\">\r\n                <thead>\r\n                    <tr>\r\n                        <th class=\"col-1\">ID</th>\r\n                        <th>Имя</th>\r\n                        <th class=\"col-2\">Последняя проверка</th>\r\n                        <th class=\"col-1\">Код ответа</th>\r\n                    </tr>\r\n                </thead>\r\n                <tbody>\r\n                    ");
				for (Url url : urls) {
					jteOutput.writeContent("\r\n                        <tr>\r\n                            <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\r\n                            <td>\r\n                                <a href=\"/urls/");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(url.getId());
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\r\n                            </td>\r\n                            <td></td>\r\n                            <td></td>\r\n                        </tr>\r\n                    ");
				}
				jteOutput.writeContent("\r\n                </tbody>\r\n            </table>\r\n        </div>\r\n    ");
			}
		}, null, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		List<Url> urls = (List<Url>)params.get("urls");
		render(jteOutput, jteHtmlInterceptor, urls);
	}
}
