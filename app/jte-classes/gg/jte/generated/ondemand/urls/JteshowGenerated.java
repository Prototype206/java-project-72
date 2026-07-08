package gg.jte.generated.ondemand.urls;
import hexlet.code.model.Url;
import java.time.format.DateTimeFormatter;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,7,7,11,11,13,13,13,19,19,19,23,23,23,27,27,27,49,49,49,49,49,3,4,5,5,5,5};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Url url, String flash, String flashType) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"container-lg mt-5\">\n            <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(url.getName());
				jteOutput.writeContent("</h1>\n            \n            <table class=\"table table-bordered table-hover mt-3\" data-test=\"url\">\n                <tbody>\n                    <tr>\n                        <td class=\"col-2\">ID</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getId());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Имя</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getName());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Дата создания</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(url.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				jteOutput.writeContent("</td>\n                    </tr>\n                </tbody>\n            </table>\n\n            <h2 class=\"mt-5\">Проверки</h2>\n            <button class=\"btn btn-primary mb-3\">Запустить проверку</button>\n            <table class=\"table table-bordered table-hover\">\n                <thead>\n                    <tr>\n                        <th class=\"col-1\">ID</th>\n                        <th class=\"col-1\">Код ответа</th>\n                        <th>title</th>\n                        <th>h1</th>\n                        <th>description</th>\n                        <th class=\"col-2\">Дата создания</th>\n                    </tr>\n                </thead>\n                <tbody>\n                </tbody>\n            </table>\n        </div>\n    ");
			}
		}, flash, flashType);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Url url = (Url)params.get("url");
		String flash = (String)params.getOrDefault("flash", null);
		String flashType = (String)params.getOrDefault("flashType", null);
		render(jteOutput, jteHtmlInterceptor, url, flash, flashType);
	}
}
