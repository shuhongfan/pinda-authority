FROM nginx:1.17.0

COPY nginx.template /etc/nginx/conf.d/nginx.template
COPY pinda-authority-ui/ /etc/nginx/html

# 通过 envsubst 命令替换 nginx.template 模板中的变量，并覆盖/etc/nginx/nginx.conf
CMD envsubst '$NGINX_HOST $DOCKER_REQUEST_DOMAIN_PREFIX' < /etc/nginx/conf.d/nginx.template > /etc/nginx/nginx.conf && nginx -g 'daemon off;'

