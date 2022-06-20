FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["PDB.csproj", "./"]
RUN dotnet restore "PDB.csproj"
COPY . .
WORKDIR "/src/"
RUN dotnet build "PDB.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "PDB.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "PDB.dll"]
