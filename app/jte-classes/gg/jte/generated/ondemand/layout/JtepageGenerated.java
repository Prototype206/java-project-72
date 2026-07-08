package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,28,28,28,29,29,29,29,30,30,30,33,33,37,37,37,51,51,51,2,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, String flash, String flashType) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Анализатор страниц</title>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\n</head>\n<body class=\"d-flex flex-column min-vh-100\">\n    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n        <div class=\"container-fluid container-xl\">\n            <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\n            <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n                <ul class=\"navbar-nav\">\n                    <li class=\"nav-item\">\n                        <a class=\"nav-link\" href=\"/urls\">Сайты</a>\n                    </li>\n                </ul>\n            </div>\n        </div>\n    </nav>\n\n\t");
		if (flash != null && !flash.isEmpty()) {
			jteOutput.writeContent("\n\t    <div class=\"alert alert-");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(flashType);
			jteOutput.setContext("div", null);
			jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\n\t        ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(flash);
			jteOutput.writeContent("\n\t        <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n\t    </div>\n\t");
		}
		jteOutput.writeContent("\n\n    <main class=\"flex-grow-1 mt-5\">\n        <div class=\"container-xl\">\n            ");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n        </div>\n    </main>\n\n    <footer class=\"footer border-top py-3 bg-light mt-auto\">\n        <div class=\"container-xl\">\n            <div class=\"text-center text-muted\">\n                built by <a href=\"https://ru.hexlet.io\" target=\"_blank\" class=\"text-decoration-none\">Hexlet</a>\n            </div>\n        </div>\n    </footer>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\" crossorigin=\"anonymous\"></script>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		String flash = (String)params.getOrDefault("flash", null);
		String flashType = (String)params.getOrDefault("flashType", null);
		render(jteOutput, jteHtmlInterceptor, content, flash, flashType);
	}
}
