public static void main(String[] args) {
    //setPort(4567);
    get("/hello", (request, responce) -> getFile(request,responce));
}

private static Object getFile(Request request, Response responce) {
    File file = new File("MYFILE");
    responce.raw().setContentType("application/octet-stream");
    responce.raw().setHeader("Content-Disposition","attachment; filename="+file.getName()+".zip");
    try {

        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(responce.raw().getOutputStream()));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file)))
        {
            ZipEntry zipEntry = new ZipEntry(file.getName());

            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer,0,len);
            }
        }
    } catch (Exception e) {
        halt(405,"server error");
    }

    return null;
