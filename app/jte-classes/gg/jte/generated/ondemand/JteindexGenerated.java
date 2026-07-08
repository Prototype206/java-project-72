package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,3,3,7,7,28,28,28,28,28,0,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String flash, String flashType) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"row\">\n            <div class=\"col-md-10 col-lg-8 mx-auto text-white\">\n                <h1 class=\"display-3 mb-0 text-dark\">Анализатор страниц</h1>\n                <p class=\"lead text-muted\">Бесплатная проверка сайтов на SEO пригодность</p>\n                \n                <form action=\"/urls\" method=\"post\" class=\"rss-form text-left\">\n                    <div class=\"row\">\n                        <div class=\"col\">\n                            <div class=\"form-floating mb-3\">\n                                <input type=\"text\" class=\"form-control\" id=\"url-input\" name=\"url\" placeholder=\"Ссылка\" aria-label=\"url\" autocomplete=\"off\" required>\n                                <label for=\"url-input\">Ссылка</label>\n                            </div>\n                        </div>\n                        <div class=\"col-auto\">\n                            <button type=\"submit\" class=\"btn btn-lg btn-primary px-sm-5 h-100\">Проверить</button>\n                        </div>\n                    </div>\n                </form>\n            </div>\n        </div>\n    ");
			}
		}, flash, flashType);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String flash = (String)params.getOrDefault("flash", null);
		String flashType = (String)params.getOrDefault("flashType", null);
		render(jteOutput, jteHtmlInterceptor, flash, flashType);
	}
}
