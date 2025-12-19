FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /src

COPY BrasilBurger/BrasilBurger.csproj BrasilBurger/
RUN dotnet restore BrasilBurger/BrasilBurger.csproj

COPY . .
WORKDIR /src/BrasilBurger

RUN dotnet publish -c Release -o /app/publish /p:UseAppHost=false


FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS runtime
WORKDIR /app

COPY --from=build /app/publish .

ENV ASPNETCORE_URLS=http://+:8080
ENV ASPNETCORE_ENVIRONMENT=Production

EXPOSE 8080

ENTRYPOINT ["dotnet", "BrasilBurger.dll"]
